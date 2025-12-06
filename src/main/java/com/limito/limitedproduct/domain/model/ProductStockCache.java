package com.limito.limitedproduct.domain.model;

import java.io.Serializable;

import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductStockCache implements Serializable {

	private int stock;
	private int reservation;

	public static ProductStockCache create(int stock, int reservation) {
		return new ProductStockCache(stock, reservation);
	}

	public void checkCanReserve(int amount) {
		int remainingStock = stock - reservation;
		if (amount > remainingStock) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_NOT_ENOUGH_STOCK);
		}
	}
}
