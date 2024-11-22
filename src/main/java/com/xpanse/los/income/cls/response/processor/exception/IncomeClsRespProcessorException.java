package com.xpanse.los.income.cls.response.processor.exception;

import lombok.Getter;

@Getter
public class IncomeClsRespProcessorException extends Exception {

	private final String message;

	public IncomeClsRespProcessorException(String message) {
		super(message);
		this.message = message;
	}

	public IncomeClsRespProcessorException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

}
