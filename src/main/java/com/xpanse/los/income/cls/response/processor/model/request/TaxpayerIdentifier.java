package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class TaxpayerIdentifier {
    private String taxpayerIdentifierType;
    private String taxpayerIdentifierValue;

}
