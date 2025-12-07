package com.limito.limitedproduct.domain.repository;

import com.limito.limitedproduct.domain.model.ProductOption;

public interface ProductOptionRepository {

	ProductOption save(ProductOption productOption);

	void soldOut(UUID productOptionId, UUID productItemId);
}
