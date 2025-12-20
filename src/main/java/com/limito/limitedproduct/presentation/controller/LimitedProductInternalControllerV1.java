package com.limito.limitedproduct.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.limito.limitedproduct.application.service.LimitedProductServiceV1;
import com.limito.limitedproduct.presentation.dto.request.CancelReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.GetOrderedProductInfoRequestV1;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.RollbackStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetInCartProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetOrderedProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/limited-products")
public class LimitedProductInternalControllerV1 {

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

	@PostMapping("/stock/reserve/cancel")
	public ResponseEntity<Void> cancelReserveStock(@Valid @RequestBody CancelReserveStockRequestV1 request) {
		limitedProductServiceV1.cancelReserveStock(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/stock/reduce")
	public ResponseEntity<Void> reduceStock(@Valid @RequestBody ReduceStockRequestV1 request) {
		limitedProductServiceV1.reduceStock(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/stock/rollback")
	public ResponseEntity<Void> rollbackStock(@Valid @RequestBody RollbackStockRequestV1 request) {
		limitedProductServiceV1.rollbackStock(request);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/ordered-products")
	public ResponseEntity<GetOrderedProductInfoResponseV1> getOrderedProductInfo(
		@Valid @RequestBody GetOrderedProductInfoRequestV1 request
	) {
		GetOrderedProductInfoResponseV1 response = limitedProductServiceV1.getOrderedProductInfo(request);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/in-cart-product/{limitedProductItemId}")
	public ResponseEntity<GetInCartProductInfoResponseV1> getInCartProductInfo(
		@NotNull(message = "상품 목록은 null일 수 없습니다.")
		@PathVariable UUID limitedProductItemId
	) {
		GetInCartProductInfoResponseV1 response = limitedProductServiceV1.getInCartProductInfo(limitedProductItemId);

		return ResponseEntity.ok(response);
	}
}
