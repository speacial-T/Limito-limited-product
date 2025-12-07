package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.repository.ProductOptionRepository;
import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {

	private final ProductOptionJpaRepository productOptionJpaRepository;

	@Override
	public void soldOut(UUID productOptionId, UUID productItemId) {
		ProductOption productOption = productOptionJpaRepository.findById(productOptionId).orElseThrow(() ->
			LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_OPTION_WRONG_UUID));

		productOption.makeItemSoldOut(productItemId);
	}
}
