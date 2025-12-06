package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.global.exception.LimitedProductErrorCode;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductItemRepositoryImpl implements ProductItemRepository {

	private final ProductItemJpaRepository productItemJpaRepository;

	@Override
	public List<ProductItem> findAllById(List<UUID> uuidList) {
		List<ProductItem> productItemList = productItemJpaRepository.findAllById(uuidList);

		validateFindAllById(uuidList, productItemList);

		return productItemList;
	}

	@Override
	public void soldOut(UUID uuid) {
		productItemJpaRepository.updateSoldOutById(uuid);
	}

	private void validateFindAllById(List<UUID> request, List<ProductItem> response) {
		if (request.size() != response.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND);
		}
	}
}
