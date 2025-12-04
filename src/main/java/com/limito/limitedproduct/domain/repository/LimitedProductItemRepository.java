package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import com.limito.limitedproduct.domain.vo.ProductItem;

public interface LimitedProductItemRepository {

	ProductItem findById(UUID id);
}
