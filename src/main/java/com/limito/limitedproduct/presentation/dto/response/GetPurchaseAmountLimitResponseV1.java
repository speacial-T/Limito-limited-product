package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public class GetPurchaseAmountLimitResponseV1 {
	private List<PurchaseAmountLimit> items;

	public static GetPurchaseAmountLimitResponseV1 of(List<UUID> list) {
		//TODO(은선):임시로 List<UUID> 형태의 목록을 받음
		return GetPurchaseAmountLimitResponseV1.builder()
			.items(list.stream().map(PurchaseAmountLimit::from).toList())
			.build();
	}

	@Builder(access = AccessLevel.PRIVATE)
	private static class PurchaseAmountLimit {
		private UUID limitedProductItemId;
		private int purchaseAmountLimit;

		private static PurchaseAmountLimit from(UUID uuid) {
			//TODO(은선):임시 데이터
			return PurchaseAmountLimit.builder()
				.limitedProductItemId(UUID.randomUUID())
				.purchaseAmountLimit(1)
				.build();
		}
	}
}
