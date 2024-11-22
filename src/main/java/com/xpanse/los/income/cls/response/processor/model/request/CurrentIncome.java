package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.List;

@Data
public class CurrentIncome {
    public List<CurrentIncomeItem> currentIncomeItems;
}
