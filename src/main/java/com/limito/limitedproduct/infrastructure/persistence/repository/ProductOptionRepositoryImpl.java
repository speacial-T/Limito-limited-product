package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalException;
import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.repository.ProductOptionRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {

	private final ProductOptionJpaRepository productOptionJpaRepository;

	@Override
	public ProductOption save(ProductOption productOption) {
		return productOptionJpaRepository.save(productOption);
	}

	public void soldOut(UUID productOptionId, UUID productItemId) {
		ProductOption productOption = productOptionJpaRepository.findById(productOptionId).orElseThrow(() ->
			LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_OPTION_WRONG_UUID));

		productOption.makeItemSoldOut(productItemId);
	}

	@Override
	public ProductOption findByIdOrElseThrow(UUID limitedProductOptionId) {
		return productOptionJpaRepository.findById(limitedProductOptionId).orElseThrow(() ->
			AppException.of(LimitedProductErrorCode.PRODUCT_OPTION_WRONG_UUID));
	}
}
