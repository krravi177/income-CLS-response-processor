package com.xpanse.los.income.cls.response.processor.service.impl;

import com.xpanse.los.income.cls.response.processor.client.IncomeWorkflowClient;
import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import com.xpanse.los.income.cls.response.processor.model.crr.IncomeCRRResponse;
import com.xpanse.los.income.cls.response.processor.model.request.Employer;
import com.xpanse.los.income.cls.response.processor.model.request.Employment;
import com.xpanse.los.income.cls.response.processor.model.request.IncomeRequest;
import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseEmployer;
import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseRequest;
import com.xpanse.los.income.cls.response.processor.model.respone.util.EmployerUtil;
import com.xpanse.los.income.cls.response.processor.model.uvr.*;
import com.xpanse.los.income.cls.response.processor.service.CLSRespProcessorService;
import com.xpanse.los.income.cls.response.processor.util.JsonMapper;
import com.xpanse.los.income.cls.response.processor.util.S3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CLSRespProcessorServiceImpl implements CLSRespProcessorService {

    private final Logger logger = LoggerFactory.getLogger(CLSRespProcessorServiceImpl.class);

    private final IncomeWorkflowClient incomeWorkflowClient;

    private final S3Util s3Util;

    private final EmployerUtil employerUtil;

    private final JsonMapper jsonMapper;

    public CLSRespProcessorServiceImpl(IncomeWorkflowClient incomeWorkflowClient, S3Util s3Util,
                                       EmployerUtil employerUtil, JsonMapper jsonMapper){
        this.incomeWorkflowClient = incomeWorkflowClient;
        this.s3Util = s3Util;
        this.employerUtil = employerUtil;
        this.jsonMapper = jsonMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    public UVRResponse processCLSResponse(UVRRequest uvrRequest, String tenantId) throws IncomeClsRespProcessorException{
        String imsId = uvrRequest.getImsId();
        List<IncomeResResponse> imsResponses = incomeWorkflowClient.getIncomeResponses(tenantId, imsId);
        IncomeOrderResponse imsRequest = incomeWorkflowClient.getRequests(tenantId, imsId);

        logger.info("Income workflow api response: {}", imsResponses.toString());
        Set<String> buckets =
                imsResponses
                        .stream()
                        .map(IncomeResResponse::getResourceDetails).flatMap(List::stream)
                        .map(ReqRespResourceDetails::getStorageAddress)
                        .collect(Collectors.toSet());


        final List<ImsResponseRequest>imsResponseRequests = new ArrayList<>();
        for(String bucket: buckets) {
            this.jsonMapper.getResponseFromS3(s3Util.getS3Object(bucket), ImsResponseRequest.class)
                    .ifPresent(imsResponseRequests::add);
        }

        String incomeRequestFilePath = imsRequest.getResourceDetails().get(0).getStorageAddress();

        UVRResponse uvrResponse = this.jsonMapper.getResponseFromS3(s3Util.getS3Object(incomeRequestFilePath), IncomeRequest.class)
                .map(incomeRequest -> {
            List<Employer> uniqueEmployees = getEmployersListFromImsResponse(incomeRequest, imsResponseRequests);
            logger.info("Unique employers: {}", uniqueEmployees);

            return prepareUVRResponse(imsId, incomeRequest, uniqueEmployees);
        }).orElse(new UVRResponse());


        uvrResponse.getEventResponseDetails().setServiceProviderResponse(uvrRequest.getServiceProviderResponse());
        uvrResponse.getEventResponseDetails().setDocumentCount(uvrRequest.getDocumentCount());
        uvrResponse.getEventResponseDetails().setDocumentReference(uvrRequest.getDocumentReference());
        logger.info("UVR Response: {}", uvrResponse);

        List<IncomeCRRResponse> incomeCRRResponseList = incomeWorkflowClient.getCRRRequests(tenantId, imsId);
        logger.info("Income crr data from s3 bucket: {}", incomeCRRResponseList);

        matchingCRRAndUVR(uvrResponse, incomeCRRResponseList);

        return uvrResponse;
    }

    /**
     * returns Unique list of employers processed based on given order.
     * the order in which podium has process the order.
     * @param incomeRequest IncomeRequest
     * @param imsResponseRequests List of ImsResponseRequest
     * @return List of Employer
     */
    private List<Employer> getEmployersListFromImsResponse(IncomeRequest incomeRequest,
                                                           List<ImsResponseRequest> imsResponseRequests){

        List<ImsResponseEmployer> uniqueImsResponses = employerUtil.processEmployersFromVendors(imsResponseRequests);
        List<Employer> requestEmployers = incomeRequest.getEventRequestDetails().getMessageBody().getDeal().getParties().get(0).getRoles().get(0).getBorrower().getEmployers();
        jsonMapper.mapEmployerFromImsResponseToRequest(requestEmployers, uniqueImsResponses);
        return requestEmployers;
    }

    private UVRResponse prepareUVRResponse(String imsId,
                                           IncomeRequest incomeRequest,
                                           List<Employer> employers) {
        UVRResponse uvrResponse = new UVRResponse();

        EventResponseDetails eventResponseDetails = new EventResponseDetails();
        eventResponseDetails.setEventType(incomeRequest.getEventRequestDetails().getEventType());
        eventResponseDetails.setServiceType("VerificationOfIncome");
        eventResponseDetails.setTransactionId(imsId);
        eventResponseDetails.setStatusCode(200);
        eventResponseDetails.setStatusDescription("Successful Response");
        eventResponseDetails.setServiceProviderResponse(new ArrayList<>());

        incomeRequest.getEventRequestDetails().getMessageBody().getDeal().getParties().get(0).getRoles().get(0).getBorrower().setEmployers(employers);
        eventResponseDetails.setMessageBody(incomeRequest.getEventRequestDetails().getMessageBody());
        uvrResponse.setEventResponseDetails(eventResponseDetails);

        return  uvrResponse;
    }

    private void matchingCRRAndUVR(UVRResponse uvrResponse, List<IncomeCRRResponse> incomeCRRResponses) {
        List<Employer> uvrEmployers = uvrResponse.getEventResponseDetails().getMessageBody().getDeal().getParties().get(0).getRoles().get(0).getBorrower().getEmployers();

        //sorting the employers by primary (active & inactive) & secondary (active & inactive).
        uvrEmployers = uvrEmployers.stream()
                .sorted(
                        Comparator.comparing((Employer employer) -> employer.getEmployment().getEmploymentClassificationType())
                                .thenComparing(employer -> employer.getEmployment().getEmploymentStatusType())
                ).toList();

        //matching CRR to UVR
        List<Employer> filteredEmployers = uvrEmployers.stream()
                .filter(employer -> {
                    Optional<IncomeCRRResponse> employmentRecordOptional = getIncomeCRRByEmpName(incomeCRRResponses, employer.getId());
                    return employmentRecordOptional.filter(employmentRecord -> matchingCRRAndUVRCondition(employmentRecord, employer.getEmployment(), employer.getId())).isPresent();
                }).toList();

        uvrResponse.getEventResponseDetails().getMessageBody().getDeal().getParties().get(0).getRoles().get(0).getBorrower().setEmployers(filteredEmployers);
    }

    private Optional<IncomeCRRResponse> getIncomeCRRByEmpName(List<IncomeCRRResponse> incomeCRRs, String name) {
        return incomeCRRs.stream().filter(incomeCRR -> name.equals(incomeCRR.getEmployerName())).findAny();
    }

    private boolean matchingCRRAndUVRCondition(IncomeCRRResponse incomeCRR, Employment employment, String empName) {
        int totalMonths = incomeCRR.getEmploymentHistory();
        String crrEmploymentMonths = String.valueOf(totalMonths);
        return incomeCRR.getEmploymentType().equalsIgnoreCase(employment.getEmploymentClassificationType())
                && incomeCRR.getEmploymentStatus().equalsIgnoreCase(employment.getEmploymentStatusType())
                && crrEmploymentMonths.equals(employment.getEmploymentMonthsOnJobCount())
                && incomeCRR.getEmployerName().equals(empName);
    }

}
