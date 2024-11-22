package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class Employer {
    private String id;
    private int sequenceNumber;
    private LegalEntity legalEntity;
    private EmployerAddress address;
    private Employment employment;
}
