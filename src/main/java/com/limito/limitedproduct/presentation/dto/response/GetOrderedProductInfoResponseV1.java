package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetOrderedProductInfoResponseV1 {

	private List<OrderedProductInfo> products;

	@Builder
	@Getter
	public static class OrderedProductInfo {

		private UUID limitedProductId;
		private UUID limitedProductOptionId;
		private UUID limitedProductItemId;
		private String name;
		private String brandName;
		private Long sellerId;
		private String color;
		private String size;
		private int price;
	}
}
