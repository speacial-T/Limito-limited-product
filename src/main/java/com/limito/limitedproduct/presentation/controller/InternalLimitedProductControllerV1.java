package com.limito.limitedproduct.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.limitedproduct.application.service.LimitedProductService;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/limited-products")
public class InternalLimitedProductControllerV1 {

	private final LimitedProductService limitedProductService;

	@GetMapping("/purchase-amount-limit")
	public ResponseEntity<?> getPurchaseAmountLimits(
		@RequestBody GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1
	) {
		GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimitResponseV1
			= limitedProductService.getPurchaseAmountLimits(getPurchaseAmountLimitRequestV1);

		return ResponseEntity.ok(getPurchaseAmountLimitResponseV1);
	}
}
