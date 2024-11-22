package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

import java.util.List;

@Data
public class OtherPayYtd {
	private double otherTotalPayYTD;
	private List<OtherPayDetail> otherPayDetailsYTD;
}
