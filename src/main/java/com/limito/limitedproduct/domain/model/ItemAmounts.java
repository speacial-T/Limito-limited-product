package com.limito.limitedproduct.domain.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.vo.ItemAmount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemAmounts {

	private Set<ItemAmount> itemAmounts;

	public void validateDuplicateId(List<UUID> requestItemIdList) {
		if (itemAmounts.size() != requestItemIdList.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
		}
	}
}
