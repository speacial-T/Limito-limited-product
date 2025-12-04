package com.limito.limitedproduct.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.limito.limitedproduct.domain.vo.Product;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.infrastructure.persistence.entity.OptionStatusEnum;

public class ProductOption {

	private UUID id;
	private String modelNo;
	private String thumbnailUrl;
	private String details;
	private String color;
	private LocalDateTime openAt;
	private OptionStatusEnum status;
	private Product product;
	private List<ProductItem> productItemList;
}
