package com.limito.limitedproduct.presentation.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockItem(
	@NotNull(message = "아이템 id는 null일 수 없습니다.")
	UUID limitedProductItemId,

	@NotNull(message = "수량은 null일 수 없습니다.")
	@Positive(message = "최소 구매 수량은 1개입니다.")
	int amount
) {
}
