package com.limito.limitedproduct.global.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductAndOptionResponse {
	private final String name;
	private final String brandName;
	private final UUID limitedProductOptionId;
	private final String thumbnailUrl;
	private final String color;
	private final String status;
	private final boolean soldOut;
	private final int minimumPrice;
}
