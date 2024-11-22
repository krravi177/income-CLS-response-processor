package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class ImsResponseEmployment {
	private Boolean employmentBorrowerSelfEmployedIndicator;
	private String employmentReportedDate;
	private String employmentStatusType;
	private String employerIdentificationNumber;
	private String employerName;
	private String employmentClassificationType;
	private String employmentStartDate;
	private String employmentEndDate;
	private Extensions extensions;
}
