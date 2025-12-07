package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.limito.limitedproduct.domain.model.ProductOption;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, UUID> {
}
