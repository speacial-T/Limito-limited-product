package com.limito.limitedproduct.global.exception;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LimitedProductErrorCode implements ErrorCode {
	PRODUCT_WRONG_UUID(HttpStatus.NOT_FOUND, "잘못된 상품 id입니다."),
	PRODUCT_OPTION_WRONG_UUID(HttpStatus.NOT_FOUND, "잘못된 상품 옵션 id입니다."),
	PRODUCT_ITEM_WRONG_UUID(HttpStatus.NOT_FOUND, "잘못된 상품 아이템 id입니다."),
	PRODUCT_WRONG_CATEGORY_ID(HttpStatus.CONFLICT, "상품 카테고리가 올바르지 않습니다. 이미 등록된 상품입니다."),
	PRODUCT_WRONG_BRAND_NAME(HttpStatus.CONFLICT, "브랜드명이 올바르지 않습니다. 이미 등록된 상품입니다.");

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
