package com.limito.limitedproduct.domain.model;

import java.util.List;

import com.limito.limitedproduct.domain.vo.ProductItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductItems {

	private List<ProductItem> productItemList;

	public static ProductItems of(List<ProductItem> productItemList) {
		return new ProductItems(productItemList);
	}
}
