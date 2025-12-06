package com.limito.limitedproduct.infrastructure.redis;

import java.util.UUID;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.ProductCacheRepository;
import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;
import com.limito.limitedproduct.infrastructure.persistence.repository.ProductItemJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCacheRepositoryImpl implements ProductCacheRepository {

	private static final String FIELD_STOCK = "stock";
	private static final String FIELD_RESERVATION = "reservation";

	private final RedisTemplate<String, String> redisTemplate;
	private final ProductItemJpaRepository productItemJpaRepository;

	private HashOperations<String, String, String> hashOps() {
		return redisTemplate.opsForHash();
	}

	private String key(UUID itemId) {
		return "item:" + itemId;
	}

	private Pair<Integer, Integer> initCache(UUID itemId) {
		String key = key(itemId);
		ProductItem productItem = productItemJpaRepository.findById(itemId).orElseThrow(() ->
			LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_WRONG_UUID)
		);

		hashOps().putIfAbsent(key, FIELD_STOCK, String.valueOf(productItem.getStock()));
		hashOps().putIfAbsent(key, FIELD_RESERVATION, "0");

		int stock = Integer.parseInt(hashOps().get(key, FIELD_STOCK));
		int reservation = Integer.parseInt(hashOps().get(key, FIELD_RESERVATION));

		return Pair.of(stock, reservation);
	}

	@Override
	public int getStock(UUID itemId) {
		String stock = hashOps().get(key(itemId), FIELD_STOCK);

		if (stock == null) {
			return initCache(itemId).getFirst();
		}

		return Integer.parseInt(stock);
	}

	@Override
	public int getReservation(UUID itemId) {
		String reservation = hashOps().get(key(itemId), FIELD_RESERVATION);

		if (reservation == null) {
			return initCache(itemId).getSecond();
		}

		return Integer.parseInt(reservation);
	}

	@Override
	public void reserve(UUID itemId, int amount) {
		checkCanReserve(itemId, amount);
		hashOps().increment(key(itemId), FIELD_RESERVATION, amount);
	}

	@Override
	public void cancelReservation(UUID itemId, int amount) {
		hashOps().increment(key(itemId), FIELD_RESERVATION, -amount);
	}

	private void checkCanReserve(UUID itemId, int amount) {
		int stock = getStock(itemId);
		int reservation = getReservation(itemId);
		int remainingStock = stock - reservation;

		if (amount > remainingStock) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_NOT_ENOUGH_STOCK);
		}
	}
}
