package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.ProductItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetPurchaseAmountLimitResponseV1 {
	private List<PurchaseAmountLimit> items;

	public static GetPurchaseAmountLimitResponseV1 of(List<ProductItem> productItemList) {
		return GetPurchaseAmountLimitResponseV1.builder()
			.items(productItemList.stream().map(PurchaseAmountLimit::from).toList())
			.build();
	}

	@Getter
	@Builder(access = AccessLevel.PRIVATE)
	public static class PurchaseAmountLimit {
		private UUID limitedProductItemId;
		private int purchaseAmountLimit;

		public static PurchaseAmountLimit from(ProductItem productItem) {
			return PurchaseAmountLimit.builder()
				.limitedProductItemId(productItem.getId())
				.purchaseAmountLimit(productItem.getPurchaseAmountLimit())
				.build();
		}
	}
}
