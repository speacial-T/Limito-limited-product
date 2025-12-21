package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import com.limito.limitedproduct.domain.model.Product;

public interface ProductRepository {

	Product findByNameAndSellerIdOrElseNull(Product product);

	Product findByIdOrElseThrow(UUID productId);

	Product findById(UUID productId);
}
