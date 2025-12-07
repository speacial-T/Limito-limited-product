package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import com.limito.limitedproduct.presentation.dto.response.ProductAndOptionResponse;

public interface ProductOptionQueryRepository {

	PagedModel<ProductAndOptionResponse> findOptionsByCategoryId(UUID categoryId, Pageable pageable);
}
