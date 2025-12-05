package com.limito.limitedproduct.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.LimitedProductItemRepository;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitedProductServiceV1 {

	private final LimitedProductItemRepository limitedProductItemRepository;

	public GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimits(
		GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1
	) {
		List<ProductItem> productItemList
			= limitedProductItemRepository.findAllById(getPurchaseAmountLimitRequestV1.itemIdList());

		return GetPurchaseAmountLimitResponseV1.of(productItemList);
	}
}
