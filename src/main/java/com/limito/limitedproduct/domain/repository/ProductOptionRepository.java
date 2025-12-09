package com.limito.limitedproduct.domain.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.ProductOption;

public interface ProductOptionRepository {

	ProductOption save(ProductOption productOption);

	void soldOut(UUID productOptionId, UUID productItemId);

	ProductOption findByIdOrElseThrow(UUID limitedProductOptionId);

	List<ProductOption> findAllByIds(Set<UUID> optionIdList);
}
