package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class Borrower {
	private String id;
	private int sequenceNumber;
	private Employers employers;
}
