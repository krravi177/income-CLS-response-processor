package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class OrderIdentifier {
	private String vendorOrderId;
	private String requestorTransactionId;
	private Lender lender;
}
