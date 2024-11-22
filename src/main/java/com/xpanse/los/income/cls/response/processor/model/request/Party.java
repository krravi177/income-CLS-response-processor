package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.List;

@Data
public class Party {
    private int sequenceNumber;
    private String id;
    private Individual individual;
    private List<Role> roles;
    private List<TaxpayerIdentifier> taxpayerIdentifiers;
    private LegalEntity legalEntity;
}
