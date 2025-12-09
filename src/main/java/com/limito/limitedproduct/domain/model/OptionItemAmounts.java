package com.limito.limitedproduct.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.application.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalException;
import com.limito.limitedproduct.domain.vo.OptionItemAmount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionItemAmounts {

	private Set<OptionItemAmount> optionItemStocks;

	public void validateDuplicateId(List<UUID> idList) {
		if (optionItemStocks.size() != idList.size()) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
		}
	}

	public Set<UUID> getOptionIdSet() {
		return new HashSet<>(
			optionItemStocks.stream()
				.map(OptionItemAmount::getOptionId)
				.toList()
		);
	}

	public void validateOptionId(List<ProductOption> productOptionList) {
		if (getOptionIdSet().size() != productOptionList.size()) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_OPTION_WRONG_UUID);
		}
	}
}
