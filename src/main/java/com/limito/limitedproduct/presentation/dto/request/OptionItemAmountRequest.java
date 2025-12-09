package com.limito.limitedproduct.presentation.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OptionItemAmountRequest(
	@NotNull(message = "옵션 id는 null일 수 없습니다.")
	UUID limitedProductOptionId,

	@NotNull(message = "아이템 id는 null일 수 없습니다.")
	UUID limitedProductItemId,

	@NotNull(message = "수량은 null일 수 없습니다.")
	@Positive(message = "수량은 1 이상이어야 합니다.")
	int amount
) {
}
