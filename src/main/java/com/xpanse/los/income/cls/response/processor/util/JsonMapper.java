package com.xpanse.los.income.cls.response.processor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import com.xpanse.los.income.cls.response.processor.model.request.Employer;
import com.xpanse.los.income.cls.response.processor.model.request.Employment;
import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseEmployer;
import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseEmployment;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JsonMapper {

    private final Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private final static String LOG_CLASS_REF = "income-cls-response-processor | JsonMapper -";
    private final ObjectMapper objectMapper;
    private final DateUtil dateUtil;
    private final ModelMapper modelMapper;

    public JsonMapper(ObjectMapper objectMapper, DateUtil dateUtil, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.dateUtil = dateUtil;
        this.modelMapper = modelMapper;
    }

    public <T> Optional<T> getResponseFromS3(ResponseInputStream<GetObjectResponse> stream, Class<T> responseType) throws IncomeClsRespProcessorException {
        try {
            T t = objectMapper.readValue(stream, responseType);
            return Optional.of(t);
        } catch (IOException e) {
            logger.error("{} getResponseFromS3 method | {}", LOG_CLASS_REF, e.getMessage());
            throw new IncomeClsRespProcessorException("Failed to parse the file from s3 bucket");
        }
    }

    public void mapEmployerFromImsResponseToRequest(List<Employer> requestEmployers,
                                                               List<ImsResponseEmployer> imsResponseEmployers) {
        requestEmployers
                .forEach(imsRequestEmployer -> {
                    Optional<ImsResponseEmployer> employerOptional = getImsResponseEmployee(imsRequestEmployer.getId(), imsResponseEmployers);
                    employerOptional.map(imsResponseEmployer -> mapResponseEmploymentToRequest(imsResponseEmployer.getEmployment()))
                            .ifPresent(imsRequestEmployer::setEmployment);
                });
    }

    private Optional<ImsResponseEmployer> getImsResponseEmployee(String empId, List<ImsResponseEmployer> imsResponseEmployers) {
        return imsResponseEmployers.stream()
                .filter(employer -> employer.getId().equals(empId))
                .findAny();
    }

    private Employment mapResponseEmploymentToRequest(ImsResponseEmployment imsResponseEmployer) {
        Employment employment = this.modelMapper.map(imsResponseEmployer, Employment.class);
        long monthsOnJobCount = this.dateUtil.getMonthsBetweenDates(imsResponseEmployer.getEmploymentStartDate(), imsResponseEmployer.getEmploymentEndDate());
        employment.setEmploymentMonthsOnJobCount(String.valueOf(monthsOnJobCount));
        return employment;
    }
}
