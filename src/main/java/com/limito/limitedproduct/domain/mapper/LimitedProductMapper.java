package com.limito.limitedproduct.domain.mapper;

import java.util.List;

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit;

public class LimitedProductMapper {

	public static GetPurchaseAmountLimitResponseV1 toGetPurchaseAmountLimitResponse(List<ProductItem> productItemList) {
		return GetPurchaseAmountLimitResponseV1.builder()
			.items(productItemList.stream()
				.map(LimitedProductMapper::toPurchaseAmountLimit)
				.toList()
			)
			.build();
	}

	public static PurchaseAmountLimit toPurchaseAmountLimit(ProductItem productItem) {
		return PurchaseAmountLimit.builder()
			.limitedProductItemId(productItem.getId())
			.purchaseAmountLimit(productItem.getPurchaseAmountLimit())
			.build();
	}
}
