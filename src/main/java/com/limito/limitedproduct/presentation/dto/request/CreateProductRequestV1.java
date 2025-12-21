package com.limito.limitedproduct.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateProductRequestV1(
	@NotNull(message = "상품 정보는 null일 수 없습니다.")
	@Valid ProductRequestInfo product
) {

	public record ProductRequestInfo(
		@NotNull(message = "카테고리 id는 null일 수 없습니다.")
		UUID categoryId,

		@NotBlank(message = "상품명이 없습니다.")
		String name,

		@NotBlank(message = "상품 코드가 없습니다.")
		String productCode,

		@NotBlank(message = "썸네일 이미지 url이 없습니다.")
		String thumbnailUrl,

		String details,

		@NotNull(message = "출시 일시는 null일 수 없습니다.")
		LocalDateTime openAt,

		String color,

		@NotNull(message = "재고 단위(sku) 목록은 null일 수 없습니다.")
		@Size(min = 1, message = "요청값에 최소 한 개의 재고 단위(sku)가 있어야 합니다.")
		@Valid List<ProductItemRequestInfo> productItems
	) {
	}

	public record ProductItemRequestInfo(
		@NotBlank(message = "크기 정보가 없습니다.")
		String size,

		@NotNull(message = "가격은 null일 수 없습니다.")
		@PositiveOrZero(message = "가격은 0원 이상의 값이어야 합니다.")
		int price,

		@Positive(message = "최소 구매 제한 수량은 1개입니다.")
		Integer purchaseAmountLimit,

		@PositiveOrZero(message = "재고는 0개 이상 입니다.")
		Integer stock
	) {
	}
}
