package com.limito.limitedproduct.infrastructure.persistence.repository.query;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.model.ProductAndOption;
import com.limito.limitedproduct.domain.repository.ProductOptionQueryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionQueryRepositoryImpl implements ProductOptionQueryRepository {

	private final ProductOptionQueryJpaRepository productOptionQueryJpaRepository;

	@Override
	public Page<ProductAndOption> findOptionsByCategoryId(UUID categoryId, Pageable pageable) {
		return productOptionQueryJpaRepository.findOptionsByCategoryId(categoryId, pageable);
	}
}
