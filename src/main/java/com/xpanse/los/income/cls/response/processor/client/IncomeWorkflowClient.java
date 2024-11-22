package com.xpanse.los.income.cls.response.processor.client;

import com.xpanse.los.income.cls.response.processor.model.crr.IncomeCRRResponse;
import com.xpanse.los.income.cls.response.processor.model.uvr.IncomeOrderResponse;
import com.xpanse.los.income.cls.response.processor.model.uvr.IncomeResResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "income-workflow-api", url = "${income.workflow.api.url}")
public interface IncomeWorkflowClient {

    /**
     * get Income Order by ims id.
     * @param tenantID String
     * @param imsId String
     * @return IncomeOrderResponse
     */
    @GetMapping("/requests/{imsId}")
    IncomeOrderResponse getRequests(@RequestHeader("X-Tenant-ID") String tenantID,
                                           @PathVariable String imsId);

    /**
     * get income request by ims id.
     * @param tenantID String
     * @param imsId String
     * @return List<IncomeResResponse>
     */
    @GetMapping("/responses/{imsId}")
    List<IncomeResResponse> getIncomeResponses(@RequestHeader("X-Tenant-ID") String tenantID, @PathVariable String imsId);

    /**
     * get CRR date by ims id.
     * @param tenantID String
     * @param imsId String
     * @return List<IncomeResResponse>
     */
    @GetMapping("/crr/{imsId}")
    List<IncomeCRRResponse> getCRRRequests(@RequestHeader("X-Tenant-ID") String tenantID, @PathVariable String imsId);
}
