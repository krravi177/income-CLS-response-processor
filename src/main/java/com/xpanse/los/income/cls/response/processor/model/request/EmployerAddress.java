package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class EmployerAddress {
	private String addressLineText;
    private String addressAdditionalLineText;
    private String addressAdditionalLineTwoText;
    private String cityName;
    private String countryName;
    private String countryCode;
    private String countyName;
    private String countyCode;
    private String postalCode;
    private String stateCode;
    private String stateName;

}
