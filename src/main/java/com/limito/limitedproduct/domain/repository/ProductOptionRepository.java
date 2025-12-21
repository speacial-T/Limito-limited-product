package com.limito.limitedproduct.domain.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.Model;

public interface ProductOptionRepository {

	void soldOut(UUID productOptionId, UUID productItemId);

	Model findByIdOrElseThrow(UUID limitedProductOptionId);

	List<Model> findAllByIds(Set<UUID> optionIdList);
}
