package com.limito.limitedproduct.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public class ProductResponse {

	@Builder
	@Getter
	public static class ProductResponseInfo {
		private UUID limitedProductId;
		private UUID categoryId;
		private String name;
		private Long sellerId;
		private String brandName;
	}

	@Builder
	@Getter
	public static class ProductOptionResponseInfo {
		private UUID limitedProductOptionId;
		private String modelNumber;
		private String thumbnailUrl;
		private String details;
		private String color;
		private LocalDateTime openAt;
		private String status;
	}

	@Builder
	@Getter
	public static class ProductItemResponseInfo {
		private UUID limitedProductItemId;
		private String size;
		private int price;
		private int purchaseAmountLimit;
		private int stock;
		private boolean soldOut;
	}
}
