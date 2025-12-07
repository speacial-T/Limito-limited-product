package com.limito.limitedproduct.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;

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
}
