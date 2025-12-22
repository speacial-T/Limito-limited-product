package com.limito.limitedproduct.domain.repository;

import java.util.UUID;

import com.limito.limitedproduct.domain.model.Product;

public interface ProductRepository {

	Product findByNameAndSellerIdOrElseGetNull(String name, Long sellerId);

	Product save(Product newProduct);

	Product findByIdOrElseThrowAppException(UUID productId);
}
