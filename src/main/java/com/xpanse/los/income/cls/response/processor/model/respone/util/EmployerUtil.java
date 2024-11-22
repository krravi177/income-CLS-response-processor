package com.xpanse.los.income.cls.response.processor.model.respone.util;

import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseEmployer;
import com.xpanse.los.income.cls.response.processor.model.respone.ImsResponseRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EmployerUtil {

    public List<ImsResponseEmployer> processEmployersFromVendors(List<ImsResponseRequest> imsResponseRequests) {
        List<ImsResponseEmployer> uniqueEmployers = new ArrayList<>();

        Map<Integer, List<ImsResponseEmployer>> vendorEmpMap = IntStream.range(0, imsResponseRequests.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> getImsResponseEmployers(imsResponseRequests.get(i))));

        Set<Integer> noOfVendors = vendorEmpMap.keySet();
        for(Integer current: noOfVendors) {

            if (1 == noOfVendors.size()) {
                getUniqueEmployerRecords(uniqueEmployers, vendorEmpMap.get(current), Collections.emptyList());
                break;
            }

            for (Integer next: noOfVendors) {
                if(!current.equals(next)) {
                    getUniqueEmployerRecords(uniqueEmployers, vendorEmpMap.get(current), vendorEmpMap.get(next));
                }
            }
        }

        return  uniqueEmployers;
    }

    private Optional<ImsResponseEmployer> getEmployerById(String id, List<ImsResponseEmployer> employers) {
        return employers.stream().filter(employer -> id.equals(employer.getId())).findAny();
    }

    private boolean checkEmployerById(String id, List<ImsResponseEmployer> employers) {
        return employers.stream().anyMatch(employer -> id.equals(employer.getId()));
    }

    private List<ImsResponseEmployer> getImsResponseEmployers(ImsResponseRequest imsResponseRequest) {
        return  imsResponseRequest.getParty().getRole().getBorrower().getEmployers().getEmployer();
    }

    private void getUniqueEmployerRecords(List<ImsResponseEmployer> uniqueEmployers,
                                          List<ImsResponseEmployer> firstVendorEmps,
                                          List<ImsResponseEmployer> secondVendorEmps){
        for (ImsResponseEmployer employer: firstVendorEmps) {
            boolean alreadyAdded = checkEmployerById(employer.getId(), uniqueEmployers);
            if(!alreadyAdded) {
                Optional<ImsResponseEmployer> vrEmployerOpt = getEmployerById(employer.getId(), secondVendorEmps);
                if (vrEmployerOpt.isEmpty()) {
                    uniqueEmployers.add(employer);
                } else {
                    ImsResponseEmployer vrEmployer = vrEmployerOpt.get();
                    if (null != employer.getEmployment() && null != employer.getEmployment().getExtensions().getPaymentHistory()) {
                        uniqueEmployers.add(employer);
                    } else if (null != vrEmployer.getEmployment() && null != vrEmployer.getEmployment().getExtensions().getPaymentHistory()) {
                        uniqueEmployers.add(vrEmployer);
                    } else {
                        uniqueEmployers.add(employer);
                    }
                }
            }
        }
    }

}
