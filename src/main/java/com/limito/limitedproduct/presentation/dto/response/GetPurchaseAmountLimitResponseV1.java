package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.vo.ProductItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPurchaseAmountLimitResponseV1 {
	private List<PurchaseAmountLimit> items;

	@Getter
	@Builder
	public static class PurchaseAmountLimit {
		private UUID limitedProductItemId;
		private int purchaseAmountLimit;
	}
}
