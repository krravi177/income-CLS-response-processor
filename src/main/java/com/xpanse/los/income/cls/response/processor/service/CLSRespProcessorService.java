package com.xpanse.los.income.cls.response.processor.service;

import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import com.xpanse.los.income.cls.response.processor.model.uvr.UVRRequest;
import com.xpanse.los.income.cls.response.processor.model.uvr.UVRResponse;

public interface CLSRespProcessorService {

    /**
     * generates UVR Records from one or more Vendor Records.
     * @param uvrRequest {@link UVRRequest}
     * @return UVRResponse
     */
    UVRResponse processCLSResponse(UVRRequest uvrRequest, String tenantId) throws IncomeClsRespProcessorException;
}
