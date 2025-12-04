package com.limito.limitedproduct.domain.vo;

import java.util.UUID;

public class ProductItem {

	private UUID id;
	private String size;
	private int price;
	private int stock;
	private boolean isSoldOut;
	private int purchaseAmountLimit;
}
