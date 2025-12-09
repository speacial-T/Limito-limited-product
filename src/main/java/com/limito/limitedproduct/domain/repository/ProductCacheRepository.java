package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import com.limito.limitedproduct.domain.model.ItemAmounts;

public interface ProductCacheRepository {

	int getStock(UUID itemId);

	int getReservation(UUID itemId);

	void reserve(UUID itemId, int amount);

	void cancelReservations(ItemAmounts itemAmounts);

	void cancelReservation(UUID itemId, int amount);

	void reduceStock(UUID itemId, int amount);

	void cancelReduction(UUID itemId, int amount);

	boolean checkSoldOut(UUID itemId);

	boolean rollbackStock(UUID itemId, int amount);
}
