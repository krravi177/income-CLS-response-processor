package com.xpanse.los.income.cls.response.processor.model.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayAmountYtd {
	private double grossPayYTD;
	private double basePayYTD;
	private double overtimePayYTD;
	private BonusPayYtd bonusPayYTD;
	private OtherPayYtd otherPayYTD;
	@JsonProperty("3pspAmountYTD")
	private int _3pspAmountYTD;
}
