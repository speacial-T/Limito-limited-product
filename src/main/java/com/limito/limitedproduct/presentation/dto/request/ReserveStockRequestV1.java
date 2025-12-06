package com.limito.limitedproduct.presentation.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ReserveStockRequestV1(

	@Size(min = 1, message = "요청값에 최소 한 개의 아이템이 있어야 합니다.")
	List<ItemAmount> items
) {
	public record ItemAmount(

		@NotNull(message = "아이템 id는 null일 수 없습니다.")
		UUID limitedProductItemId,

		@NotNull(message = "수량은 null일 수 없습니다.")
		@Positive(message = "최소 구매 수량은 1개입니다.")
		int amount
	) {
	}
}
