package com.limito.limitedproduct.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.limito.limitedproduct.domain.model.ProductItem;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductItemJpaRepository extends JpaRepository<ProductItem, UUID> {

	@Modifying
	@Query("UPDATE ProductItem SET isSoldOut = true WHERE id = :uuid")
	void updateSoldOutById(@Param("uuid") UUID uuid);
}
