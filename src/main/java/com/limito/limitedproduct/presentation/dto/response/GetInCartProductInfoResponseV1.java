package com.limito.limitedproduct.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetInCartProductInfoResponseV1 {

	private String name;
	private String brandName;
	private Long sellerId;
	private String color;
	private String size;
	private int price;
	private String thumbnailUrl;
	private String productStatus;
	private Boolean isSoldOut;
}
