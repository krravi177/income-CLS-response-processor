package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class CurrentIncomeItem {
    private String id;
    private int sequenceNumber;
    private double currentIncomeMonthlyTotalAmount;
    private boolean employmentIncomeIndicator;
    private String incomeType;
    private Map<String, String> links;
}
