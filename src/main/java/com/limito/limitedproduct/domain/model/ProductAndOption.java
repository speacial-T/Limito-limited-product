package com.limito.limitedproduct.domain.model;

import java.util.UUID;

import com.limito.limitedproduct.domain.vo.OptionStatus;

import lombok.Getter;

@Getter
public class ProductAndOption {
	private final String name;
	private final String brandName;
	private final UUID limitedProductOptionId;
	private final String thumbnailUrl;
	private final String color;
	private final String status;
	private final boolean soldOut;
	private final int minimumPrice;

	public ProductAndOption(
		String name,
		String brandName,
		UUID limitedProductOptionId,
		String thumbnailUrl,
		String color,
		OptionStatus status,
		boolean soldOut,
		int minimumPrice
	) {
		this.name = name;
		this.brandName = brandName;
		this.limitedProductOptionId = limitedProductOptionId;
		this.thumbnailUrl = thumbnailUrl;
		this.color = color;
		this.status = status.name();
		this.soldOut = soldOut;
		this.minimumPrice = minimumPrice;
	}
}
