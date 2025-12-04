package com.limito.limitedproduct.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_limited_products")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_id", nullable = false, updatable = false)
	private UUID productId;

	@Column(name = "category_id", nullable = false)
	private UUID categoryId;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "seller_id", nullable = false, updatable = false)
	private Long sellerId;

	@Column(name = "brand_name", nullable = false, length = 100)
	private String sellerName;
}
