package com.xpanse.los.income.cls.response.processor.exception.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private List<ApiError> error;

}
