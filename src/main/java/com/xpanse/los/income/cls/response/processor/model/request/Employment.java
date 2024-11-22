package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class Employment {
    private boolean employmentBorrowerSelfEmployedIndicator;
    private String employmentStatusType;
    private String employmentClassificationType;
    private String employmentStartDate;
    private String employmentEndDate;
    //private String employmentMonthlyIncomeAmount;
    private String employmentMonthsOnJobCount;
    private String employmentReportedDate;


}

