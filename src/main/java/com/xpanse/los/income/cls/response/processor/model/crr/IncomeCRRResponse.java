package com.xpanse.los.income.cls.response.processor.model.crr;

import lombok.Data;

@Data
public class IncomeCRRResponse {

	private String employerIdNumber;

	private String employerName;

	private String employmentType;

	private String employmentStatus;

	private int employmentHistory;

	private int incomeHistory;

	private String asOfDate;

}