package com.limito.limitedproduct.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateProductResponseV1 {

	private UUID modelId;
	private UUID categoryId;
	private String name;
	private String productCode;
	private Long sellerId;
	private String thumbnailUrl;
	private String details;
	private LocalDateTime openAt;
	private String status;
	private List<OptionInfo> displayOptions;
	private List<SkuInfo> skus;
}
