package com.xpanse.los.income.cls.response.processor.model.uvr;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UVRRequest {

    @NotNull
    private String imsId;
    private List<ServiceProviderResponse> serviceProviderResponse;
    private int documentCount;
    private List<DocumentReference> documentReference;

}
