package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

public interface ProductCacheRepository {

	int getStock(UUID itemId);

	int getReservation(UUID itemId);

	void reserve(UUID itemId, int amount);

	void cancelReservation(UUID itemId, int amount);
}
