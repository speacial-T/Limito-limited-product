package com.limito.limitedproduct.domain.vo;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductItem {

	private UUID id;
	private String size;
	private int price;
	private int stock;
	private boolean isSoldOut;
	private int purchaseAmountLimit;
}
