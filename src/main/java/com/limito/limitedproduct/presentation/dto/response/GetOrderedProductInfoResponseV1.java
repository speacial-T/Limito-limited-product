package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetOrderedProductInfoResponseV1 {

	private List<OrderedProductInfo> products;

	@Builder
	@Getter
	public static class OrderedProductInfo {

		private String name;
		private String brandName;
		private Long sellerId;
		private String color;
		private String size;
		private int price;
	}
}
