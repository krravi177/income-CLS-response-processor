package com.xpanse.los.income.cls.response.processor.model.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayAmount {
	private double grossPay;
	private double basePay;
	private double overTimePay;
	private BonusPay bonusPay;
	private OtherPay otherPay;
	@JsonProperty("3pspAmount")
	private int _3pspAmount;
}
