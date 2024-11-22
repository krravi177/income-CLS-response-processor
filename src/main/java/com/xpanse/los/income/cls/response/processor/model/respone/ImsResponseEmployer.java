package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class ImsResponseEmployer {
	private String id;
	private int sequenceNumber;
	private String legalEntity;
	private Individual individual;
	private Address address;
	private ImsResponseEmployment employment;
}
