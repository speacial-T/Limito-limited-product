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
	@Valid CreateProductRequestV1.ProductRequestInfo product,

	@NotNull(message = "아이템 목록은 null일 수 없습니다.")
	@Size(min = 1, message = "요청값에 최소 한 개의 아이템이 있어야 합니다.")
	@Valid List<ProductItemRequestInfo> productItems

) {

	public record ProductRequestInfo(
		@NotNull(message = "카테고리 id는 null일 수 없습니다.")
		UUID categoryId,

		@NotBlank(message = "상품명이 없습니다.")
		String name,

		@NotBlank(message = "브랜드명이 없습니다.")
		String brandName,

		@NotBlank(message = "모델번호가 없습니다.")
		String modelNumber,

		@NotBlank(message = "썸네일 이미지 url이 없습니다.")
		String thumbnailUrl,

		String details,

		@NotNull(message = "출시 일시는 null일 수 없습니다.")
		LocalDateTime openAt,

		String color
	) {
	}

	public record ProductItemRequestInfo(
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
