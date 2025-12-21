package com.limito.limitedproduct.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.limito.common.exception.AppException;
import com.limito.common.security.audit.BaseEntity;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.domain.vo.OptionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "p_limited_models")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Model extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_model_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "thumbnail_url", nullable = false)
	private String thumbnailUrl;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	@Column(name = "open_at", nullable = false, updatable = false)
	private LocalDateTime openAt;

	@Column(name = "status", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private OptionStatus status = OptionStatus.READY;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false, updatable = false)
	private Product product;

	@OneToOne
	@JoinColumn(name = "display_option_group_id", unique = true, nullable = false, updatable = false)
	private OptionGroup displayOptionGroup;

	@Builder
	private Model(
		String thumbnailUrl,
		String details,
		LocalDateTime openAt,
		Product product,
		OptionGroup displayOptionGroup
	) {
		this.thumbnailUrl = thumbnailUrl;
		this.details = details;
		this.openAt = openAt;
		this.product = product;
		this.displayOptionGroup = displayOptionGroup;
	}

	public void validateProductOptionOpened() {
		if (status != OptionStatus.OPEN) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_OPTION_NOT_OPENED);
		}
	}

	public void initStatus() {
		if (!this.openAt.isAfter(LocalDateTime.now())) {
			this.status = OptionStatus.OPEN;
		}
	}

	public void attachProduct(Product product) {
		this.product = product;
	}

	public void attachProductItems(List<Sku> skuList) {
		// itemList = skuList;
		for (Sku sku : skuList) {
			sku.attachProductOption(this);
		}
		checkAllSoldOut();
		updateMinimumPrice();
	}

	public void makeItemSoldOut(UUID productItemId) {
		//TODO: refactor - for-if
		// TODO: refactor - for-if
		// for (Sku sku : itemList) {
		// 	if (sku.getId().equals(productItemId)) {
		// 		sku.soldOut();
		// 		checkAllSoldOut();
		// 		return;
		// 	}
		// }
		throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_WRONG_UUID);
	}

	private void checkAllSoldOut() {
		//TODO: refactor - for-if

		// for (Sku sku : itemList) {
		// 	if (!sku.isSoldOut()) {
		// 		this.isSoldOut = false;
		// 		return;
		// 	}
		// 	this.isSoldOut = true;
		// }
	}

	private void updateMinimumPrice() {
		int min = Integer.MAX_VALUE;
		// for (Sku sku : itemList) {
		// 	min = Integer.min(min, sku.getPrice());
		// }
	}

	public void rollbackStockIfMatches(UUID optionId, UUID itemId, int amount) {
		if (id.equals(optionId)) {
			rollbackStock(itemId, amount);
		}
	}

	private void rollbackStock(UUID itemId, int amount) {
		// TODO: refactor - for-if

		// for (Sku sku : itemList) {
		// 	if (sku.getId().equals(itemId)) {
		// 		sku.rollbackStock(amount);
		// 		changeIsSoldOutToFalse();
		// 	}
		// }
	}

	private void changeIsSoldOutToFalse() {
		// if (isSoldOut) {
		// 	isSoldOut = false;
		// }
	}
}
