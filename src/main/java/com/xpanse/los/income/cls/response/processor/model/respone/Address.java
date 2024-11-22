package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class Address {
	private String addressLineText;
	private String addressAdditionalLineText;
	private String addressAdditionalLineTwoText;
	private String cityName;
	private String countryCode;
	private String countryName;
	private String countyCode;
	private String countyName;
	private String postalCode;
	private String stateCode;
	private String stateName;
}
