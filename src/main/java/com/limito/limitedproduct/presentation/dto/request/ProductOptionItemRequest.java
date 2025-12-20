package com.limito.limitedproduct.presentation.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ProductOptionItemRequest(
	@NotNull(message = "상품 id는 null일 수 없습니다.")
	UUID limitedProductId,

	@NotNull(message = "옵션 id는 null일 수 없습니다.")
	UUID limitedProductOptionId,

	@NotNull(message = "아이템 id는 null일 수 없습니다.")
	UUID limitedProductItemId
) {
}
