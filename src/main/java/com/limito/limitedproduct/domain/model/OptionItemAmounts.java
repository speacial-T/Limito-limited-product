package com.limito.limitedproduct.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.vo.OptionItemAmount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionItemAmounts {

	private Set<OptionItemAmount> optionItemAmounts;

	public void validateDuplicateId(List<UUID> idList) {
		if (optionItemAmounts.size() != idList.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
		}
	}

	public Set<UUID> getOptionIdSet() {
		return new HashSet<>(
			optionItemAmounts.stream()
				.map(OptionItemAmount::getOptionId)
				.toList()
		);
	}

	public void validateOptionId(List<ProductOption> productOptionList) {
		if (getOptionIdSet().size() != productOptionList.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_OPTION_WRONG_UUID);
		}
	}
}
