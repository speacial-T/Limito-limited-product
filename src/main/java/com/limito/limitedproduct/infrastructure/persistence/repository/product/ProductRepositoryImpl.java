package com.limito.limitedproduct.infrastructure.persistence.repository.product;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public Product findByNameAndSellerIdOrElseNull(Product product) {
		return productJpaRepository.findByNameAndSellerId(product.getName(), product.getSellerId())
			.orElse(product);
	}

	@Override
	public Product findByIdOrElseThrow(UUID productId) {
		return productJpaRepository.findById(productId)
			.orElseThrow(() -> AppException.of(LimitedProductErrorCode.PRODUCT_WRONG_UUID));
	}

	@Override
	public Product findById(UUID productId) {
		return productJpaRepository.findById(productId)
			.orElseThrow(() -> AppException.of(LimitedProductErrorCode.PRODUCT_WRONG_UUID));
	}
}
