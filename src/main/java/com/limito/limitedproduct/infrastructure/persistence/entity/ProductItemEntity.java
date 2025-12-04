package com.limito.limitedproduct.infrastructure.persistence.entity;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_limited_product_items")
public class ProductItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_item_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "size", nullable = false, length = 10)
	@ColumnDefault("'one size'")
	private String size;

	@Column(name = "price", nullable = false)
	@ColumnDefault("-1")
	private int price;

	@Column(name = "stock", nullable = false)
	@ColumnDefault("0")
	private int stock;

	@Column(name = "soldout", nullable = false)
	@ColumnDefault("FALSE")
	private boolean isSoldOut;

	@Column(name = "purchase_amount_limit", nullable = false)
	private int purchaseAmountLimit;

	@ManyToOne
	@JoinColumn(name = "limited_product_option_id", nullable = false, updatable = false)
	private ProductOptionEntity productOption;
}
