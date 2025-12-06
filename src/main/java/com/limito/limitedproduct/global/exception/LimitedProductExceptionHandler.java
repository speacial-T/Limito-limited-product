package com.limito.limitedproduct.global.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.limito.limitedproduct.global.response.LimitedProductErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // 공통 핸들러보다 먼저 먹게
@RestControllerAdvice
// @RestControllerAdvice(basePackages = "com.limito.limitedproduct")
public class LimitedProductExceptionHandler {

	@ExceptionHandler(LimitedProductInternalException.class)
	public ResponseEntity<LimitedProductErrorResponse> handleLimitedProductInternalException(
		LimitedProductInternalException exception
	) {
		log.error("[LimitedProductInternalException] status={} errorCode={} message={}",
			exception.getStatus(), exception.getErrorCode(), exception.getMessage(), exception);

		return LimitedProductErrorResponse.limitedProductErrorResponse(
			exception.getStatus(), exception.getErrorCode(), exception.getMessage()
		);
	}
}
