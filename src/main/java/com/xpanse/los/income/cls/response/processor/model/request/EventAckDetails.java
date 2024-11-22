package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class EventAckDetails {
  private String eventType;
  private String transactionCode;
  private String imsId;
}
