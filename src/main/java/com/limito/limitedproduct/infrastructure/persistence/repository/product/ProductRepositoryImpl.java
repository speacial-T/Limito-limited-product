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
	public Product findByNameAndSellerIdOrElseGetNull(String name, Long sellerId) {
		return productJpaRepository.findByNameAndSellerId(name, sellerId)
			.orElse(null);
	}

	@Override
	public Product save(Product newProduct) {
		return productJpaRepository.save(newProduct);
	}

	@Override
	public Product findByIdOrElseThrowAppException(UUID productId) {
		return productJpaRepository.findById(productId)
			.orElseThrow(() -> AppException.of(LimitedProductErrorCode.PRODUCT_WRONG_UUID));
	}
}
