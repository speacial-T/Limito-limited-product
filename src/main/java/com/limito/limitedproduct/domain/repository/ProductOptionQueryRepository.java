package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.limito.limitedproduct.domain.model.ProductAndOption;

public interface ProductOptionQueryRepository {

	Page<ProductAndOption> findOptionsByCategoryId(UUID categoryId, Pageable pageable);
}
