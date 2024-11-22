package com.xpanse.los.income.cls.response.processor.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** audit message model class */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditMessage {
	
	/** applicationName field */
	private String applicationName;
	/** action field */
	private String action;
	/** action payload field */
	private IncomeRequest actionPayload;
	/** auditDate field */
	private String auditDate;

}
