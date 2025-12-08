package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.limitedproduct.domain.vo.ProductItem;

public interface ProductItemJpaRepository extends JpaRepository<ProductItem, UUID> {
}
