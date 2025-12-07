package com.limito.limitedproduct.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.vo.OptionStatus;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOption {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_option_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "model_number", nullable = false, unique = true, length = 50)
	private String modelNumber;

	@Column(name = "thumbnail_url", nullable = false)
	private String thumbnailUrl;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	@Column(name = "color", nullable = false, length = 50)
	private String color = "one color";

	@Column(name = "open_at", nullable = false, updatable = false)
	private LocalDateTime openAt;

	@Column(name = "status", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private OptionStatus status = OptionStatus.READY;

	@Column(name = "limited_product_id", nullable = false, updatable = false)
	private UUID productId;

	@OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductItem> itemList;

	@Builder
	private ProductOption(
		String modelNumber,
		String thumbnailUrl,
		String details,
		LocalDateTime openAt,
		String color
	) {
		this.modelNumber = modelNumber;
		this.thumbnailUrl = thumbnailUrl;
		this.details = details;
		this.openAt = openAt;
		this.color = color;
	}

	public void validateProductOptionOpened() {
		if (status != OptionStatus.OPEN) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_OPTION_NOT_OPENED);
		}
	}

	public void initStatus() {
		if (this.openAt.isBefore(LocalDateTime.now())) {
			this.status = OptionStatus.OPEN;
		}
	}

	public void attachProduct(UUID productId) {
		this.productId = productId;
	}

	public void attachProductItems(List<ProductItem> productItemList) {
		itemList = productItemList;
		for (ProductItem productItem : productItemList) {
			productItem.attachProductOption(this);
		}
	}

	public void makeItemSoldOut(UUID productItemId) {
		for (ProductItem productItem : itemList) {
			if (productItem.getId().equals(productItemId)) {
				productItem.soldOut();
				return;
			}
		}
		throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_WRONG_UUID);
	}
}
