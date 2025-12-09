package com.limito.limitedproduct.domain.model;

import java.util.UUID;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "p_limited_products",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "unique_name_and_seller_id",
			columnNames = {"name", "seller_id"}
		)
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "category_id", nullable = false)
	private UUID categoryId;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "seller_id", nullable = false, updatable = false)
	private Long sellerId;

	@Column(name = "brand_name", nullable = false, length = 100)
	private String brandName;

	@Builder
	private Product(
		UUID categoryId,
		String name,
		Long sellerId,
		String brandName
	) {
		//TODO: 카테고리ID 검증
		this.categoryId = categoryId;
		this.name = name;
		this.sellerId = sellerId;
		this.brandName = brandName;
	}

	public void validateCategoryId(UUID categoryId) {
		if (!this.categoryId.equals(categoryId)) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_WRONG_CATEGORY_ID);
		}
	}

	public void validateBrandName(String brandName) {
		if (!this.brandName.equals(brandName)) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_WRONG_BRAND_NAME);
		}
	}
}
