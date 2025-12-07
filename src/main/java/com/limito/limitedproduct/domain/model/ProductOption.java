package com.limito.limitedproduct.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import com.limito.limitedproduct.domain.vo.OptionStatus;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_limited_product_options")
public class ProductOption {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_product_option_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "model_number", nullable = false, unique = true, length = 50)
	private String modelNo;

	@Column(name = "thumbnail_url", nullable = false, length = 255)
	private String thumbnailUrl;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;

	@Column(name = "color", nullable = false, length = 50)
	@ColumnDefault("'one color'")
	private String color;

	@Column(name = "open_at", nullable = false, updatable = false)
	private LocalDateTime openAt;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'READY'")
	private OptionStatus status;

	@ManyToOne
	@JoinColumn(name = "limited_product_id", nullable = false, updatable = false)
	private Product product;

	@OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductItem> itemList;

	public void validateProductOptionOpened() {
		if (status != OptionStatus.OPEN) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_OPTION_NOT_OPENED);
		}
	}
}
