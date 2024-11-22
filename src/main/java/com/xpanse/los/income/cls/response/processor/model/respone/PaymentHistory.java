package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class PaymentHistory {
	private String payDate;
	private String payCycle;
	private PayPeriod payPeriod;
	private String payDescription;
	private String payPeriodHours;
	private int basePayRate;
	private PayAmount payAmount;
	private int basePayDetails;
	private int deductions;
	private int payDistributions;
}
