package com.limito.limitedproduct.application.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.data.web.PagedModel;

import com.limito.limitedproduct.domain.model.ItemAmounts;
import com.limito.limitedproduct.domain.model.OptionItemAmounts;
import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.model.ProductAndOption;
import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.vo.ItemAmount;
import com.limito.limitedproduct.domain.vo.OptionItemAmount;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.global.dto.response.ProductAndOptionResponse;
import com.limito.limitedproduct.presentation.dto.request.CancelReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductItemRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.ItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.OptionItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.RollbackStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetInCartProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetOrderedProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductsByCategoryResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit;
import com.limito.limitedproduct.presentation.dto.response.ProductItemResponseInfo;
import com.limito.limitedproduct.presentation.dto.response.ProductOptionResponseInfo;
import com.limito.limitedproduct.presentation.dto.response.ProductResponseInfo;

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

	public static OptionItemAmounts toOptionItemAmounts(RollbackStockRequestV1 rollbackStockRequestV1) {
		return OptionItemAmounts.builder()
			.optionItemAmounts(new HashSet<>(
				rollbackStockRequestV1.products()
					.stream()
					.map(LimitedProductMapper::toOptionItemAmount)
					.toList()
			))
			.build();
	}

	public static OptionItemAmount toOptionItemAmount(OptionItemAmountRequest optionItemAmountRequest) {
		return OptionItemAmount.builder()
			.optionId(optionItemAmountRequest.limitedProductOptionId())
			.itemId(optionItemAmountRequest.limitedProductItemId())
			.amount(optionItemAmountRequest.amount())
			.build();
	}

	public static ItemAmounts toItemAmounts(CancelReserveStockRequestV1 cancelReserveStockRequestV1) {
		return ItemAmounts.builder()
			.itemAmounts(new HashSet<>(
				cancelReserveStockRequestV1.items()
					.stream()
					.map(LimitedProductMapper::toItemAmount)
					.toList()
			))
			.build();
	}

	public static ItemAmount toItemAmount(ItemAmountRequest itemAmountRequest) {
		return ItemAmount.builder()
			.itemId(itemAmountRequest.limitedProductItemId())
			.amount(itemAmountRequest.amount())
			.build();
	}

	public static GetPurchaseAmountLimitResponseV1 toGetPurchaseAmountLimitResponse(List<ProductItem> productItemList) {
		return GetPurchaseAmountLimitResponseV1.builder()
			.items(productItemList.stream()
				.map(LimitedProductMapper::toPurchaseAmountLimit)
				.toList()
			)
			.build();
	}

	public static PurchaseAmountLimit toPurchaseAmountLimit(ProductItem productItem) {
		return PurchaseAmountLimit.builder()
			.limitedProductItemId(productItem.getId())
			.purchaseAmountLimit(productItem.getPurchaseAmountLimit())
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

	public static GetProductsByCategoryResponseV1 toGetProductsByCategoryResponse(
		UUID categoryId,
		String category,
		PagedModel<ProductAndOptionResponse> productAndOptionList
	) {
		return GetProductsByCategoryResponseV1.builder()
			.categoryId(categoryId)
			.category(category)
			.data(productAndOptionList)
			.build();
	}

	public static ProductAndOptionResponse toProductAndOptionResponse(ProductAndOption productAndOption) {
		return ProductAndOptionResponse.builder()
			.name(productAndOption.getName())
			.brandName(productAndOption.getBrandName())
			.limitedProductOptionId(productAndOption.getLimitedProductOptionId())
			.thumbnailUrl(productAndOption.getThumbnailUrl())
			.color(productAndOption.getColor())
			.status(productAndOption.getStatus())
			.soldOut(productAndOption.isSoldOut())
			.minimumPrice(productAndOption.getMinimumPrice())
			.build();
	}

	public static GetOrderedProductInfoResponseV1 toGetOrderedProductInfoResponse(
		List<GetOrderedProductInfoResponseV1.OrderedProductInfo> orderedProductInfoList
	) {
		return GetOrderedProductInfoResponseV1.builder()
			.products(orderedProductInfoList)
			.build();
	}

	public static GetOrderedProductInfoResponseV1.OrderedProductInfo toOrderedProductInfo(
		Product product,
		ProductOption productOption,
		ProductItem productItem
	) {
		return GetOrderedProductInfoResponseV1.OrderedProductInfo
			.builder()
			.limitedProductId(product.getId())
			.limitedProductOptionId(productOption.getId())
			.limitedProductItemId(productItem.getId())
			.name(product.getName())
			.brandName(product.getBrandName())
			.sellerId(product.getSellerId())
			.color(productOption.getColor())
			.size(productItem.getSize())
			.price(productItem.getPrice())
			.build();
	}

	public static GetInCartProductInfoResponseV1 toGetInCartProductInfoResponseV1(
		Product product,
		ProductOption productOption,
		ProductItem productItem
	) {
		return GetInCartProductInfoResponseV1.builder()
			.productName(product.getName())
			.sellerId(product.getSellerId())
			.brandName(product.getBrandName())
			.productColor(productOption.getColor())
			.thumbnailUrl(productOption.getThumbnailUrl())
			.productStatus(productOption.getStatus().name())
			.productSize(productItem.getSize())
			.productPrice(productItem.getPrice())
			.isSoldOut(productItem.isSoldOut())
			.build();
	}
}
