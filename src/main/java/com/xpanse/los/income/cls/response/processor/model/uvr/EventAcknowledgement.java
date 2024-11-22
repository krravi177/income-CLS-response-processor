package com.xpanse.los.income.cls.response.processor.model.uvr;

import lombok.Data;

@Data
public class EventAcknowledgement {

    private String eventType;

    private String transactionCode;

    private String imsId;
}
