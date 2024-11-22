package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

import java.util.List;

@Data
public class OtherPay {
	private double otherPayTotal;
	private List<OtherPayDetail> otherPayDetails;
}
