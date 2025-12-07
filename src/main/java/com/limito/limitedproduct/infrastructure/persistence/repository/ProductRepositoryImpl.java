package com.limito.limitedproduct.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

	private final ProductJpaRepository productJpaRepository;

	@Override
	public Product findByNameAndSellerIdOrElseGetNull(String name, Long sellerId) {
		return productJpaRepository.findByNameAndSellerId(name, sellerId).orElse(null);
	}

	@Override
	public Product save(Product newProduct) {
		return productJpaRepository.save(newProduct);
	}
}
