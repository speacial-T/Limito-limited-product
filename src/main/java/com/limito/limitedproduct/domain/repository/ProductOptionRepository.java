package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

public interface ProductOptionRepository {

	void soldOut(UUID productOptionId, UUID productItemId);
}
