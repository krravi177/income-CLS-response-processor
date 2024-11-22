package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

@Data
public class ImsResponseRequest {
	private Loans loans;
	private Party party;
	private Attachments attachments;
}
