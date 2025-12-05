package com.limito.limitedproduct.domain.repository;

import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.vo.ProductItem;

public interface LimitedProductItemRepository {

	List<ProductItem> findAllById(List<UUID> uuids);
}
