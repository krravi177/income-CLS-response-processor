package com.xpanse.los.income.cls.response.processor.model.uvr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UVRResponseAck {

    private EventAcknowledgement eventAckDetails;

}
