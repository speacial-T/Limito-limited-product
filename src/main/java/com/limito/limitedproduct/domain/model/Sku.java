package com.limito.limitedproduct.domain.model;

import java.util.UUID;

import com.limito.common.exception.AppException;
import com.limito.common.security.audit.BaseEntity;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_skus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sku extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "sku_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "stock", nullable = false)
	private int stock = 0;

	@Column(name = "is_sold_out", nullable = false)
	private boolean isSoldOut = false;

	@Column(name = "max_amount", nullable = false)
	private int maxAmount = Integer.MAX_VALUE;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "model_id", nullable = false, updatable = false)
	private Model model;

	@OneToOne
	@JoinColumn(name = "selling_option_group_id", unique = true, nullable = false, updatable = false)
	private OptionGroup sellingOptionGroup;

	@Builder
	private Sku(
		int price,
		Integer stock,
		Integer maxAmount,
		Model model,
		OptionGroup sellingOptionGroup
	) {
		this.price = price;
		this.isSoldOut = (stock == null || stock == 0);
		this.model = model;
		this.sellingOptionGroup = sellingOptionGroup;

		if (stock != null) {
			this.stock = stock;
		}

		if (maxAmount != null) {
			this.maxAmount = maxAmount;
		}
	}

	public void validateProductOptionOpened() {
		model.validateProductOptionOpened();
	}

	public void validateProductItemIsNotSoldOut() {
		if (isSoldOut) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_IS_SOLD_OUT);
		}
	}

	public void validatePurchaseAmountLimit(int amount) {
		if (amount > maxAmount) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_OVER_PURCHASE_AMOUNT_LIMIT);
		}
	}

	public void attachProductOption(Model model) {
		this.model = model;
	}

	public void soldOut() {
		isSoldOut = true;
		stock = 0;
	}

	public void rollbackStock(int amount) {
		this.stock += amount;
		this.isSoldOut = false;
	}
}
