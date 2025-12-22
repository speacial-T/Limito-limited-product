package com.limito.limitedproduct.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetInCartProductInfoResponseV1 {

	private String productName;
	private Long sellerId;
	private String brandName;
	private String productColor;
	private String thumbnailUrl;
	private String productStatus;
	private String productSize;
	private int productPrice;
	private Boolean isSoldOut;
}
