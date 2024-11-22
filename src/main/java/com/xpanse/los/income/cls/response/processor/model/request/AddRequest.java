package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** add request model class */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRequest {

	/** imsId field */
	private String imsId;

	/** tenantRefId field */
	private String tenantRefId;

	/** transType field */
	private String transType;

	/** transDate field */
	private String transDate;

	/** status field */
	private String status;

	/** resourceDetails field */
	private ResourceDetails resourceDetails;

}
