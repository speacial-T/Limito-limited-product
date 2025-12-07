package com.limito.limitedproduct.global.exception;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LimitedProductErrorCode implements ErrorCode {
	PRODUCT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "상품 판매 아이템을 찾을 수 없습니다."),
	PRODUCT_WRONG_CATEGORY_ID(HttpStatus.NOT_FOUND, "상품 카테고리가 올바르지 않습니다. 이미 등록된 상품입니다."),
	PRODUCT_WRONG_BRAND_NAME(HttpStatus.NOT_FOUND, "브랜드명이 올바르지 않습니다. 이미 등록된 상품입니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
