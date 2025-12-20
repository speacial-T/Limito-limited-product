package com.limito.limitedproduct.presentation.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductResponseInfo {
	private UUID limitedProductId;
	private UUID categoryId;
	private String name;
	private Long sellerId;
	private String brandName;
}
