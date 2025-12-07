package com.limito.limitedproduct.global.exception;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LimitedProductErrorCode implements ErrorCode {
	PRODUCT_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "상품 판매 아이템을 찾을 수 없습니다."),
	PRODUCT_WRONG_CATEGORY_ID(HttpStatus.NOT_FOUND, "상품 카테고리가 올바르지 않습니다. 새로운 상품을 등록하시려면 상품명을 변경해주세요.");

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
