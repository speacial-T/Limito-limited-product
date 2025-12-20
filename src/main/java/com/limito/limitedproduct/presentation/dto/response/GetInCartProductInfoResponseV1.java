package com.limito.limitedproduct.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetInCartProductInfoResponseV1 {

	private String productName;
	private String productColor;
	private String productSize;
	private int productPrice;
	private String brandName;
	private String thumbnailUrl;
	private Long sellerId;
	private String productStatus;
	private Boolean isSoldOut;
}
