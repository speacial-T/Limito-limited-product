package com.limito.limitedproduct.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Generated;

public class LimitedProductInternalException extends RuntimeException {

	private final HttpStatus status;
	private final String errorCode;

	public LimitedProductInternalException(LimitedProductInternalErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.errorCode = errorCode.getErrorCode();
	}

	public static LimitedProductInternalException of(LimitedProductInternalErrorCode errorCode) {
		return new LimitedProductInternalException(errorCode);
	}

	@Generated
	public HttpStatus getStatus() {
		return this.status;
	}

	@Generated
	public String getErrorCode() {
		return this.errorCode;
	}
}
