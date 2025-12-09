package com.limito.limitedproduct.presentation.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductItemResponseInfo {
	private UUID limitedProductItemId;
	private String size;
	private int price;
	private int purchaseAmountLimit;
	private int stock;
	private boolean soldOut;
}
