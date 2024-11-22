package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class Address {
    private String addressLineText;
    private String addressAdditionalLineText;
    private String cityName;
    private String countyName;
    private String postalCode;
    private String stateCode;

}

