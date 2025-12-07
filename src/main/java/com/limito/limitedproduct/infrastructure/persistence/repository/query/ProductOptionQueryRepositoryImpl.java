package com.limito.limitedproduct.infrastructure.persistence.repository.query;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.repository.ProductOptionQueryRepository;
import com.limito.limitedproduct.presentation.dto.response.ProductAndOptionResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionQueryRepositoryImpl implements ProductOptionQueryRepository {

	private final ProductOptionQueryJpaRepository productOptionQueryJpaRepository;

	@Override
	public PagedModel<ProductAndOptionResponse> findOptionsByCategoryId(UUID categoryId, Pageable pageable) {
		Page<ProductAndOptionResponse> productAndOptionList
			= productOptionQueryJpaRepository.findOptionsByCategoryId(categoryId, pageable);
		return new PagedModel<>(productAndOptionList);
	}
}
