package com.limito.limitedproduct.domain.repository;

import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.ProductItem;

public interface ProductItemRepository {

	List<ProductItem> findAllById(List<UUID> uuids);

	void soldOut(UUID uuid);
}
