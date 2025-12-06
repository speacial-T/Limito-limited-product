package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

public interface ProductCacheRepository {

	void initCache(UUID itemId, int stock);

	int getStock(UUID itemId);

	int getReservation(UUID itemId);

	void reserve(UUID itemId, int reservation);
}
