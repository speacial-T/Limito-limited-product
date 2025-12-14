package com.limito.limitedproduct.presentation.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GetOrderedProductInfoRequestV1(
	@NotNull(message = "상품 목록은 null일 수 없습니다.")
	@Size(min = 1, message = "요청값에 최소 한 개의 상품이 있어야 합니다.")
	@Valid List<ProductOptionItemRequest> products
) {
}
