package com.limito.limitedproduct.infrastructure.persistence.repository.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.limitedproduct.domain.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

	Optional<Product> findByNameAndSellerId(String name, Long sellerId);
}
