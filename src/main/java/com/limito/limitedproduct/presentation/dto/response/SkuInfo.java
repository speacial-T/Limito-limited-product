package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SkuInfo {

	private UUID skuId;
	private int price;
	private int stock;
	private boolean isSoldOut;
	private int maxAmount;
	private List<OptionInfo> sellingOptions;
}
