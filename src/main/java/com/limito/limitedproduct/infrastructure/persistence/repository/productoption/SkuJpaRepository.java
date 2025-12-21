package com.limito.limitedproduct.infrastructure.persistence.repository.productoption;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.limitedproduct.domain.model.Sku;

public interface SkuJpaRepository extends JpaRepository<Sku, UUID> {
}
