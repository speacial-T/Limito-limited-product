package com.limito.limitedproduct.domain.repository;

import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.Sku;

public interface SkuRepository {

	List<Sku> findAllById(List<UUID> uuids);

	List<Sku> saveAll(List<Sku> skuList);
}
