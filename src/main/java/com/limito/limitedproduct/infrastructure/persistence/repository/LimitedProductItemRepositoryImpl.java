package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.LimitedProductItemRepository;
import com.limito.limitedproduct.global.exception.LimitedProductErrorCode;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LimitedProductItemRepositoryImpl implements LimitedProductItemRepository {

	private final LimitedProductItemJpaRepository limitedProductItemJpaRepository;

	@Override
	public List<ProductItem> findAllById(List<UUID> uuidList) {
		List<ProductItem> productItemList = limitedProductItemJpaRepository.findAllById(uuidList);

		if (productItemList.size() < uuidList.size()) {
			throw AppException.of(
				LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getStatus(),
				LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage()
			);
		}

		return productItemList;
	}
}
