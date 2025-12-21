package com.limito.limitedproduct.infrastructure.persistence.repository.productoption;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.model.Sku;
import com.limito.limitedproduct.domain.repository.SkuRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SkuRepositoryImpl implements SkuRepository {

	private final SkuJpaRepository skuJpaRepository;

	@Override
	public List<Sku> findAllById(List<UUID> uuidList) {
		List<Sku> skuList = skuJpaRepository.findAllById(uuidList);

		validateFindAllById(uuidList, skuList);

		return skuList;
	}

	@Override
	public List<Sku> saveAll(List<Sku> skuList) {
		return skuJpaRepository.saveAll(skuList);
	}

	private void validateFindAllById(List<UUID> request, List<Sku> response) {
		if (request.size() != response.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_WRONG_UUID);
		}
	}
}
