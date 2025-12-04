package com.limito.limitedproduct.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

@Service
public class LimitedProductService {

	public GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimits(
		GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1
	) {

		return GetPurchaseAmountLimitResponseV1.of(List.of());
	}
}
