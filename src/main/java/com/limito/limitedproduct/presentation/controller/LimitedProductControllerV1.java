package com.limito.limitedproduct.presentation.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.limito.limitedproduct.application.service.LimitedProductServiceV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductsByCategoryResponseV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/limited-products")
@RequiredArgsConstructor
public class LimitedProductControllerV1 {

	private final LimitedProductServiceV1 limitedProductServiceV1;

	@PostMapping("")
	public ResponseEntity<CreateProductResponseV1> createProduct(
		// @AuthenticationPrincipal UserDetailImpl userDetail,
		@Valid @RequestBody CreateProductRequestV1 request
	) {
		//TODO: common 라이브러리를 통해 인증 정보 가져오는 코드로 변경하기
		Long userId = 1L;

		CreateProductResponseV1 response = limitedProductServiceV1.createProduct(userId, request);

		return ResponseEntity
			.created(
				URI.create(
					String.format(
						"/api/v1/limited-products/%s",
						response.getProductOptionInfo()
							.getLimitedProductOptionId()
					)
				)
			)
			.body(response);
	}

	@GetMapping("/{limitedProductOptionId}")
	public ResponseEntity<GetProductOptionResponseV1> getProductOption(@PathVariable UUID limitedProductOptionId) {
		GetProductOptionResponseV1 response = limitedProductServiceV1.getProductOption(limitedProductOptionId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<GetProductsByCategoryResponseV1> getProductsByCategory(
		@RequestParam(name = "categoryId") UUID categoryId,
		Pageable pageable
	) {
		GetProductsByCategoryResponseV1 response = limitedProductServiceV1.getProductsByCategory(categoryId, pageable);

		return ResponseEntity.ok(response);
	}
}
