package com.limito.limitedproduct.domain.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.application.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalException;
import com.limito.limitedproduct.domain.vo.ItemAmount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemAmounts {

	private Set<ItemAmount> itemAmounts;

	public void validateDuplicateId(List<UUID> requestItemIdList) {
		if (itemAmounts.size() != requestItemIdList.size()) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
		}
	}
}
