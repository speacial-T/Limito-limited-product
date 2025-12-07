package com.limito.limitedproduct.domain.mapper;

import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.global.dto.response.ProductItemResponseInfo;
import com.limito.limitedproduct.global.dto.response.ProductOptionResponseInfo;
import com.limito.limitedproduct.global.dto.response.ProductResponseInfo;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductItemRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductRequestInfo;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;

public class LimitedProductMapper {

	public static Product toProduct(Long sellerId, ProductRequestInfo productRequestInfo) {
		return Product.builder()
			.categoryId(productRequestInfo.categoryId())
			.name(productRequestInfo.name())
			.sellerId(sellerId)
			.brandName(productRequestInfo.brandName())
			.build();
	}

	public static ProductOption toProductOption(ProductRequestInfo productRequestInfo) {
		return ProductOption.builder()
			.modelNumber(productRequestInfo.modelNumber())
			.thumbnailUrl(productRequestInfo.thumbnailUrl())
			.details(productRequestInfo.details())
			.openAt(productRequestInfo.openAt())
			.color(productRequestInfo.color())
			.build();
	}

	public static ProductItem toProductItem(ProductItemRequestInfo productItemRequestInfo) {
		return ProductItem.builder()
			.size(productItemRequestInfo.size())
			.price(productItemRequestInfo.price())
			.stock(productItemRequestInfo.stock())
			.purchaseAmountLimit(productItemRequestInfo.purchaseAmountLimit())
			.build();
	}

	public static CreateProductResponseV1 toCreateProductResponse(Product product, ProductOption productOption) {
		return CreateProductResponseV1.builder()
			.productInfo(
				ProductResponseInfo.builder()
					.limitedProductId(product.getId())
					.categoryId(product.getCategoryId())
					.name(product.getName())
					.sellerId(product.getSellerId())
					.brandName(product.getBrandName())
					.build()
			)
			.productOptionInfo(
				ProductOptionResponseInfo.builder()
					.limitedProductOptionId(productOption.getId())
					.modelNumber(productOption.getModelNumber())
					.thumbnailUrl(productOption.getThumbnailUrl())
					.details(productOption.getDetails())
					.color(productOption.getColor())
					.openAt(productOption.getOpenAt())
					.status(productOption.getStatus().name())
					.soldOut(productOption.isSoldOut())
					.minimumPrice(productOption.getMinimumPrice())
					.build()
			)
			.productItems(
				productOption.getItemList()
					.stream()
					.map(LimitedProductMapper::toProductItemResponseInfo)
					.toList()
			)
			.build();
	}

	public static GetProductOptionResponseV1 toGetProductOptionResponse(Product product, ProductOption productOption) {
		return GetProductOptionResponseV1.builder()
			.productInfo(
				ProductResponseInfo.builder()
					.limitedProductId(product.getId())
					.categoryId(product.getCategoryId())
					.name(product.getName())
					.sellerId(product.getSellerId())
					.brandName(product.getBrandName())
					.build()
			)
			.productOptionInfo(
				ProductOptionResponseInfo.builder()
					.limitedProductOptionId(productOption.getId())
					.modelNumber(productOption.getModelNumber())
					.thumbnailUrl(productOption.getThumbnailUrl())
					.details(productOption.getDetails())
					.color(productOption.getColor())
					.openAt(productOption.getOpenAt())
					.status(productOption.getStatus().name())
					.soldOut(productOption.isSoldOut())
					.minimumPrice(productOption.getMinimumPrice())
					.build()
			)
			.productItems(
				productOption.getItemList()
					.stream()
					.map(LimitedProductMapper::toProductItemResponseInfo)
					.toList()
			)
			.build();
	}

	private static ProductItemResponseInfo toProductItemResponseInfo(ProductItem productItem) {
		return ProductItemResponseInfo.builder()
			.limitedProductItemId(productItem.getId())
			.size(productItem.getSize())
			.price(productItem.getPrice())
			.purchaseAmountLimit(productItem.getPurchaseAmountLimit())
			.stock(productItem.getStock())
			.soldOut(productItem.isSoldOut())
			.build();
	}
}
