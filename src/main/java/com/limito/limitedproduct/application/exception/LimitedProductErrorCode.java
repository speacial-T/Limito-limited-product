package com.limito.limitedproduct.application.exception;

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
	PRODUCT_ITEM_DUPLICATE_UUID(HttpStatus.BAD_REQUEST, "중복된 옵션 ID가 존재합니다."),
	PRODUCT_OPTION_NOT_OPENED(HttpStatus.BAD_REQUEST, "판매중인 상품이 아닙니다."),
	PRODUCT_ITEM_IS_SOLD_OUT(HttpStatus.BAD_REQUEST, "품절된 상품입니다."),
	PRODUCT_ITEM_OVER_PURCHASE_AMOUNT_LIMIT(HttpStatus.BAD_REQUEST, "최대 주문 가능 수량을 초과하였습니다."),
	PRODUCT_NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "재고 부족");

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
