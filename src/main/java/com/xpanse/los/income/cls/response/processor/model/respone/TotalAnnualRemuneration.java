package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class TotalAnnualRemuneration {
	private int year;
	private PayAmountYtd payAmountYTD;
	private int deductionsAmountYTD;
}
