package com.xpanse.los.income.cls.response.processor.exception.handler;

import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import com.xpanse.los.income.cls.response.processor.exception.model.ApiError;
import com.xpanse.los.income.cls.response.processor.exception.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class IncomeClsRespProcessorControllerAdvice {

    /**
     * Handle income response validation exception api error response.
     *
     * @param exception the exception
     * @return the api error response
     */
    @ExceptionHandler(IncomeClsRespProcessorException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleIncomeResponseValidationException(final IncomeClsRespProcessorException exception) {
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(Collections.singletonList(ApiError.builder().message(exception.getMessage()).build()))
                .build();
    }

    /**
     * Handle method argument not valid exception api error response.
     *
     * @param exception the exception
     * @return the api error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<ApiError> errors = exception.getFieldErrors()
                .stream().map(e -> e.getField() + " " + e.getDefaultMessage())
                .map(e -> ApiError.builder().message(e).build()).toList();
        return ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(errors)
                .build();
    }


}
