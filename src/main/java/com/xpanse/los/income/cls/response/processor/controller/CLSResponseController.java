package com.xpanse.los.income.cls.response.processor.controller;

import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import com.xpanse.los.income.cls.response.processor.model.uvr.UVRRequest;
import com.xpanse.los.income.cls.response.processor.model.uvr.UVRResponse;
import com.xpanse.los.income.cls.response.processor.service.CLSRespProcessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CLSResponseController {

    private final CLSRespProcessorService clsRespProcessorService;

    public CLSResponseController(CLSRespProcessorService clsRespProcessorService){
        this.clsRespProcessorService = clsRespProcessorService;
    }

    /**
     * generate client response.
     * @param uvrRequest {@link UVRRequest}
     * @return UVRResponse
     */
    @PostMapping(path = "/process")
    @ResponseStatus(HttpStatus.OK)
    public UVRResponse processCLSResponse(@RequestHeader("X-Tenant-ID") String tenantId,
                                          @RequestBody @Valid UVRRequest uvrRequest) throws IncomeClsRespProcessorException {
        return clsRespProcessorService.processCLSResponse(uvrRequest, tenantId);
    }
}