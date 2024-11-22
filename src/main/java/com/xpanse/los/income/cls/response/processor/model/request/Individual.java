package com.xpanse.los.income.cls.response.processor.model.request;

import lombok.Data;

import java.util.List;

@Data
public class Individual {
    private Name name;
    private List<ContactPoint> contactPoints;

}

