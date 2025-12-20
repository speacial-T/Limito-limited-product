package com.limito.limitedproduct.infrastructure.persistence.repository.productoption;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.domain.vo.ProductItem;

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

	private void validateFindAllById(List<UUID> request, List<ProductItem> response) {
		if (request.size() != response.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_WRONG_UUID);
		}
	}
}
