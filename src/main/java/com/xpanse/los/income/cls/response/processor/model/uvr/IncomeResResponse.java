package com.xpanse.los.income.cls.response.processor.model.uvr;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class IncomeResResponse {

	private Long id;

	private String imsId;

	private String tenantRefId;

	private LocalDateTime imsTransDate;

	private String imsStatus;

	private List<ReqRespResourceDetails> resourceDetails;

}