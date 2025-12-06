package com.limito.limitedproduct.infrastructure.redis;

import java.util.UUID;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.repository.ProductCacheRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCacheRepositoryImpl implements ProductCacheRepository {

	private static final String FIELD_STOCK = "stock";
	private static final String FIELD_RESERVATION = "reservation";

	private final RedisTemplate<String, String> redisTemplate;

	private HashOperations<String, String, String> hashOps() {
		return redisTemplate.opsForHash();
	}

	private String key(UUID itemId) {
		return "item:" + itemId;
	}

	@Override
	public void initCache(UUID itemId, int stock) {
		String key = key(itemId);
		hashOps().put(key, FIELD_STOCK, String.valueOf(stock));
		hashOps().put(key, FIELD_RESERVATION, "0");
	}

	@Override
	public int getStock(UUID itemId) {
		String stock = hashOps().get(key(itemId), FIELD_STOCK);
		return (stock == null) ? 0 : Integer.parseInt(stock);
	}

	@Override
	public int getReservation(UUID itemId) {
		String reservation = hashOps().get(key(itemId), FIELD_RESERVATION);
		return (reservation == null) ? 0 : Integer.parseInt(reservation);
	}

	@Override
	public void reserve(UUID itemId, int reservation) {
		hashOps().put(key(itemId), FIELD_RESERVATION, String.valueOf(reservation));
	}
}
