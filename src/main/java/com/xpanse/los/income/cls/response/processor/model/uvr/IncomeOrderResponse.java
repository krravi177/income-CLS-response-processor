package com.xpanse.los.income.cls.response.processor.model.uvr;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class IncomeOrderResponse {

	private Long id;

	private String imsId;

	private String tenantRefId;

	private String imsTransType;

	private LocalDateTime imsTransDate;

	private String imsStatus;

	private List<ReqRespResourceDetails> resourceDetails;

}