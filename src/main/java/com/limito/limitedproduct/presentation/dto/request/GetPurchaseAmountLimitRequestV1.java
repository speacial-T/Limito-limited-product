package com.limito.limitedproduct.presentation.dto.request;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.Size;

public record GetPurchaseAmountLimitRequestV1(

	@Size(min = 1, message = "요청값에 최소 한 개의 아이템이 있어야 합니다.")
	Set<UUID> itemIdList
) {
}
