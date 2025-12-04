package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.domain.repository.LimitedProductItemRepository;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.global.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.infrastructure.persistence.entity.ProductItemEntity;
import com.limito.limitedproduct.infrastructure.persistence.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LimitedProductItemRepositoryImpl implements LimitedProductItemRepository {

	private final LimitedProductItemJpaRepository limitedProductItemJpaRepository;

	@Override
	public ProductItem findById(UUID id) {
		ProductItemEntity productItemEntity = limitedProductItemJpaRepository.findById(id)
			.orElseThrow(() ->
				AppException.of(
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getStatus(),
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage()
				)
			);

		return ProductMapper.toDomain(productItemEntity);
	}
}
