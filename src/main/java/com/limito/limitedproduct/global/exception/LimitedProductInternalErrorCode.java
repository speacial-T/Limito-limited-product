package com.limito.limitedproduct.global.exception;

import org.springframework.http.HttpStatus;

import com.limito.common.code.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LimitedProductInternalErrorCode implements ErrorCode {
	PRODUCT_ITEM_WRONG_UUID(HttpStatus.BAD_REQUEST, "E001", "잘못된 한정판매 상품 옵션 ID입니다."),
	PRODUCT_NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "E002", "재고 부족"),
	PRODUCT_OPTION_NOT_OPENED(HttpStatus.BAD_REQUEST, "E003", "판매중인 상품이 아닙니다."),
	PRODUCT_ITEM_IS_SOLD_OUT(HttpStatus.BAD_REQUEST, "E004", "품절된 상품입니다."),
	PRODUCT_ITEM_OVER_PURCHASE_AMOUNT_LIMIT(HttpStatus.BAD_REQUEST, "E005", "최대 주문 가능 수량을 초과하였습니다."),
	PRODUCT_ITEM_DUPLICATE_UUID(HttpStatus.BAD_REQUEST, "E006", "중복된 옵션 ID가 존재합니다.");

	private final HttpStatus status;
	@Getter
	private final String errorCode;
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
