package com.limito.limitedproduct.infrastructure.persistence.repository.productoption;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.domain.vo.ProductItem;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductItemRepositoryImpl implements ProductItemRepository {

	private final ProductItemJpaRepository productItemJpaRepository;

	@Override
	public List<ProductItem> findAllByIdSet(Set<UUID> itemIdSet) {
		return productItemJpaRepository.findAllById(itemIdSet);
	}
}
