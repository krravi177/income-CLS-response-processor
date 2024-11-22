package com.xpanse.los.income.cls.response.processor.model.uvr;

import com.xpanse.los.income.cls.response.processor.enums.StorageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqRespResourceDetails {

	private StorageType storageType;
	
	private String storageAddress;

	private String resourceType;
	
}
