package com.xpanse.los.income.cls.response.processor.model.uvr;

import com.xpanse.los.income.cls.response.processor.model.request.MessageBody;
import lombok.Data;

import java.util.List;

@Data
public class EventResponseDetails {

    private String eventType;

    private String serviceType;

    private String transactionCode;

    private String transactionId;

    private String orderId;

    private Integer statusCode;

    private String statusDescription;

    private MessageBody messageBody;

    private List<ServiceProviderResponse> serviceProviderResponse;

    private Integer documentCount;

    private List<DocumentReference> documentReference;

}
