package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

@Data
public class Role {
    private String id;
    private String partyRoleType;
    private Borrower borrower;

}

