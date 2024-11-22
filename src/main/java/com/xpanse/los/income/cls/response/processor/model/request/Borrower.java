package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.List;

@Data
public class Borrower {
    private String borrowerBirthDate;
    private List<Residence> residences;
    private List<Employer> employers;
    private CurrentIncome currentIncome;
}

