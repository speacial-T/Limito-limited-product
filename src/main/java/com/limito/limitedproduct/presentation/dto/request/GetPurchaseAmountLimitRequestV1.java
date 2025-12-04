package com.limito.limitedproduct.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record GetPurchaseAmountLimitRequestV1(
	List<UUID> itemIdList
) {
}
