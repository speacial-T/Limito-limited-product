package com.limito.limitedproduct.domain.vo;

import java.util.UUID;

import com.limito.limitedproduct.application.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalException;
import com.limito.limitedproduct.domain.model.ProductOption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_product_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductItem {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_item_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "size", nullable = false, length = 10)
	private String size = "one size";

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "stock", nullable = false)
	private int stock = 0;

	@Column(name = "sold_out", nullable = false)
	private boolean isSoldOut = false;

	@Column(name = "purchase_amount_limit", nullable = false)
	private int purchaseAmountLimit = Integer.MAX_VALUE;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "limited_product_option_id", nullable = false, updatable = false)
	private ProductOption productOption;

	@Builder
	private ProductItem(
		String size,
		int price,
		Integer stock,
		Integer purchaseAmountLimit
	) {
		this.price = price;
		this.isSoldOut = (stock == null || stock == 0);

		if (size != null && !size.isEmpty()) {
			this.size = size;
		}

		if (stock != null) {
			this.stock = stock;
		}

		if (purchaseAmountLimit != null) {
			this.purchaseAmountLimit = purchaseAmountLimit;
		}
	}

	public void validateProductOptionOpened() {
		productOption.validateProductOptionOpened();
	}

	public void validateProductItemIsNotSoldOut() {
		if (isSoldOut) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_IS_SOLD_OUT);
		}
	}

	public void validatePurchaseAmountLimit(int amount) {
		if (amount > purchaseAmountLimit) {
			throw LimitedProductInternalException.of(
				LimitedProductInternalErrorCode.PRODUCT_ITEM_OVER_PURCHASE_AMOUNT_LIMIT);
		}
	}

	public void attachProductOption(ProductOption productOption) {
		this.productOption = productOption;
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
