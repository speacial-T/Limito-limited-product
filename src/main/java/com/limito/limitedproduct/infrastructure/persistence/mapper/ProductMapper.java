package com.limito.limitedproduct.infrastructure.persistence.mapper;

import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.infrastructure.persistence.entity.ProductItemEntity;

public class ProductMapper {

	public static ProductItem toDomain(ProductItemEntity productItemEntity) {
		return ProductItem.builder()
			.id(productItemEntity.getId())
			.size(productItemEntity.getSize())
			.price(productItemEntity.getPrice())
			.stock(productItemEntity.getStock())
			.isSoldOut(productItemEntity.isSoldOut())
			.purchaseAmountLimit(productItemEntity.getPurchaseAmountLimit())
			.build();
	}
}
