package com.limito.limitedproduct.global.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record LimitedProductErrorResponse(String errorCode, String message) {

	public static ResponseEntity<LimitedProductErrorResponse> limitedProductErrorResponse(
		HttpStatus status, String errorCode, String message
	) {
		return ResponseEntity
			.status(status)
			.body(new LimitedProductErrorResponse(errorCode, message));
	}

}
