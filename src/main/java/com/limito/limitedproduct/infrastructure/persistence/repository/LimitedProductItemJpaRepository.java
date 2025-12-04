package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.limitedproduct.infrastructure.persistence.entity.ProductItemEntity;

public interface LimitedProductItemJpaRepository extends JpaRepository<ProductItemEntity, UUID> {
}
