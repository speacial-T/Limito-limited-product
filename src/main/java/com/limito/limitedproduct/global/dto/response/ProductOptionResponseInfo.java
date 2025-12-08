package com.limito.limitedproduct.global.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductOptionResponseInfo {
	private UUID limitedProductOptionId;
	private String modelNumber;
	private String thumbnailUrl;
	private String details;
	private String color;
	private LocalDateTime openAt;
	private String status;
}
