package com.limito.limitedproduct.infrastructure.persistence.repository.query;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.limito.limitedproduct.domain.model.ProductAndOption;
import com.limito.limitedproduct.domain.model.ProductOption;

public interface ProductOptionQueryJpaRepository extends JpaRepository<ProductOption, UUID> {

	@Query("""
		SELECT new com.limito.limitedproduct.domain.model.ProductAndOption(
			p.name,
			p.brandName,
			o.id,
			o.thumbnailUrl,
			o.color,
			o.status,
			o.isSoldOut,
			o.minimumPrice
		)
		FROM ProductOption o
		JOIN Product p
			ON o.productId = p.id
		WHERE p.categoryId = :categoryId
		ORDER BY
			o.status DESC,
			o.isSoldOut ASC,
			o.openAt DESC
				""")
	Page<ProductAndOption> findOptionsByCategoryId(
		@Param("categoryId") UUID categoryId,
		Pageable pageable
	);
}
