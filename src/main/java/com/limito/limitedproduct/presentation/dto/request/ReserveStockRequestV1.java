package com.limito.limitedproduct.presentation.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReserveStockRequestV1(
	@NotNull(message = "아이템 목록은 null일 수 없습니다.")
	@Size(min = 1, message = "요청값에 최소 한 개의 아이템이 있어야 합니다.")
	List<StockItem> items
) {

}
