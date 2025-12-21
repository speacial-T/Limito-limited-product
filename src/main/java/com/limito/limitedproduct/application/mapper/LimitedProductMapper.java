package com.limito.limitedproduct.application.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.data.web.PagedModel;

import com.limito.limitedproduct.domain.model.ItemAmounts;
import com.limito.limitedproduct.domain.model.Model;
import com.limito.limitedproduct.domain.model.Option;
import com.limito.limitedproduct.domain.model.OptionGroup;
import com.limito.limitedproduct.domain.model.OptionItemAmounts;
import com.limito.limitedproduct.domain.model.OptionValue;
import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.model.ProductAndOption;
import com.limito.limitedproduct.domain.model.Sku;
import com.limito.limitedproduct.domain.vo.ItemAmount;
import com.limito.limitedproduct.domain.vo.OptionItemAmount;
import com.limito.limitedproduct.domain.vo.OptionType;
import com.limito.limitedproduct.global.dto.response.ProductAndOptionResponse;
import com.limito.limitedproduct.presentation.dto.request.CancelReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductItemRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.ItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.OptionItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.RollbackStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetOrderedProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductsByCategoryResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit;
import com.limito.limitedproduct.presentation.dto.response.OptionInfo;
import com.limito.limitedproduct.presentation.dto.response.SkuInfo;

public class LimitedProductMapper {

	public static Product toProduct(Long userId, ProductRequestInfo productRequestInfo) {
		return Product.builder()
			.categoryId(productRequestInfo.categoryId())
			.name(productRequestInfo.name())
			.productCode(productRequestInfo.productCode())
			.sellerId(userId)
			.build();
	}

	public static Model toModel(ProductRequestInfo productRequestInfo, Product product, OptionGroup optionGroup) {
		return Model.builder()
			.thumbnailUrl(productRequestInfo.thumbnailUrl())
			.details(productRequestInfo.details())
			.openAt(productRequestInfo.openAt())
			.product()
			.displayOptionGroup(optionGroup)
			.build();
	}

	public static Sku toSku(ProductItemRequestInfo productItemRequestInfo, Model model, OptionGroup optionGroup) {
		return Sku.builder()
			.price(productItemRequestInfo.price())
			.stock(productItemRequestInfo.stock())
			.maxAmount(productItemRequestInfo.purchaseAmountLimit())
			.model(model)
			.sellingOptionGroup(optionGroup)
			.build();
	}

	public static Option toOption(String name, OptionType optionType) {
		return Option.builder()
			.name(name)
			.optionType(optionType)
			.build();
	}

	public static OptionValue toOptionValue(Option option, String value) {
		return OptionValue.builder()
			.option(option)
			.value(value)
			.build();
	}

	public static OptionGroup toOptionGroup(List<OptionValue> optionValueList) {
		return OptionGroup.builder()
			.optionValueList(optionValueList)
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

	public static GetPurchaseAmountLimitResponseV1 toGetPurchaseAmountLimitResponse(List<Sku> skuList) {
		return GetPurchaseAmountLimitResponseV1.builder()
			.items(skuList.stream()
				.map(LimitedProductMapper::toPurchaseAmountLimit)
				.toList()
			)
			.build();
	}

	public static PurchaseAmountLimit toPurchaseAmountLimit(Sku sku) {
		return PurchaseAmountLimit.builder()
			.limitedProductItemId(sku.getId())
			.purchaseAmountLimit(sku.getMaxAmount())
			.build();
	}

	public static CreateProductResponseV1 toCreateProductResponse(List<Sku> skuList) {
		Model model = skuList.get(0).getModel();
		Product product = model.getProduct();

		return CreateProductResponseV1.builder()
			.modelId(model.getId())
			.categoryId(product.getCategoryId())
			.name(product.getName())
			.productCode(product.getProductCode())
			.sellerId(product.getSellerId())
			.thumbnailUrl(model.getThumbnailUrl())
			.details(model.getDetails())
			.displayOptions(
				model.getDisplayOptionGroup()
					.getOptionValueList()
					.stream()
					.map(optionValue -> toOptionInfo(optionValue.getOption(), optionValue))
					.toList()
			)
			.skus(
				skuList.stream()
					.map(LimitedProductMapper::toSkuInfo)
					.toList()
			)
			.build();
	}

	public static OptionInfo toOptionInfo(Option option, OptionValue optionValue) {
		return OptionInfo.builder()
			.name(option.getName())
			.value(optionValue.getValue())
			.build();
	}

	public static SkuInfo toSkuInfo(Sku sku) {
		return SkuInfo.builder()
			.skuId(sku.getId())
			.price(sku.getPrice())
			.stock(sku.getStock())
			.isSoldOut(sku.isSoldOut())
			.maxAmount(sku.getMaxAmount())
			.sellingOptions(
				sku.getSellingOptionGroup()
					.getOptionValueList()
					.stream()
					.map(optionValue -> toOptionInfo(optionValue.getOption(), optionValue))
					.toList()
			)
			.build();
	}

	public static GetProductOptionResponseV1 toGetProductOptionResponse(Product product, Model model) {
		return GetProductOptionResponseV1.builder()
			.productInfo(
				ProductResponseInfo.builder()
					.limitedProductId(product.getId())
					.categoryId(product.getCategoryId())
					.name(product.getName())
					.sellerId(product.getSellerId())
					// .brandName(product.getBrandName())
					.build()
			)
			.productOptionInfo(
				ProductOptionResponseInfo.builder()
					.limitedProductOptionId(model.getId())
					// .productCode(model.getModelNumber())
					.thumbnailUrl(model.getThumbnailUrl())
					.details(model.getDetails())
					// .color(model.getColor())
					.openAt(model.getOpenAt())
					.status(model.getStatus().name())
					// .soldOut(model.isSoldOut())
					.minimumPrice(model.getMinimumPrice())
					.build()
			)
			// .productItems(
			// 	model.getItemList()
			// 		.stream()
			// 		.map(LimitedProductMapper::toProductItemResponseInfo)
			// 		.toList()
			// )
			.build();
	}

	private static SkuInfo toProductItemResponseInfo(Sku sku) {
		return SkuInfo.builder()
			.skuId(sku.getId())
			// .size(sku.getSize())
			.price(sku.getPrice())
			.purchaseAmountLimit(sku.getMaxAmount())
			.stock(sku.getStock())
			.soldOut(sku.isSoldOut())
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
		Model model,
		Sku sku
	) {
		return GetOrderedProductInfoResponseV1.OrderedProductInfo
			.builder()
			.limitedProductId(product.getId())
			.limitedProductOptionId(model.getId())
			.limitedProductItemId(sku.getId())
			.name(product.getName())
			// .brandName(product.getBrandName())
			// .sellerId(product.getSellerId())
			// .color(model.getColor())
			// .size(sku.getSize())
			.price(sku.getPrice())
			.build();
	}
}
