package com.limito.limitedproduct.infrastructure.redis;

import java.util.UUID;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
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

	@Override
	public void checkCache(UUID itemId) {
		String stock = hashOps().get(key(itemId), FIELD_STOCK);
		String reservation = hashOps().get(key(itemId), FIELD_RESERVATION);

		if (stock == null || reservation == null) {
			initCache(itemId);
		}
	}

	private void initCache(UUID itemId) {
		String key = key(itemId);
		ProductItem productItem = productItemJpaRepository.findById(itemId).orElseThrow(() ->
			LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_WRONG_UUID)
		);

		hashOps().put(key, FIELD_STOCK, String.valueOf(productItem.getStock()));
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
