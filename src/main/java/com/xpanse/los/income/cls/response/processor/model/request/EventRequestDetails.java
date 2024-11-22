package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestDetails {
    private String eventType;
    private String eventCode;
    private String transactionCode;
    private MessageBody messageBody;
    private List<Attachment> attachment;
}

