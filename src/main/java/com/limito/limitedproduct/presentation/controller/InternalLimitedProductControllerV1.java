package com.limito.limitedproduct.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.limitedproduct.application.service.LimitedProductServiceV1;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/limited-products")
public class InternalLimitedProductControllerV1 {

	private final LimitedProductServiceV1 limitedProductServiceV1;

	@PostMapping("/purchase-amount-limit")
	public ResponseEntity<GetPurchaseAmountLimitResponseV1> getPurchaseAmountLimits(
		@Valid @RequestBody GetPurchaseAmountLimitRequestV1 request
	) {
		GetPurchaseAmountLimitResponseV1 response = limitedProductServiceV1.getPurchaseAmountLimits(request);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/stock/reserve")
	public ResponseEntity<Void> reserveStock(@Valid @RequestBody ReserveStockRequestV1 request) {
		limitedProductServiceV1.reserveStock(request);

		return ResponseEntity.ok().build();
	}
}
