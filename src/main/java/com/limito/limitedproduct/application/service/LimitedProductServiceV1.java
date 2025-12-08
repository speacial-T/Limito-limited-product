package com.limito.limitedproduct.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.limito.limitedproduct.application.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.application.exception.LimitedProductInternalException;
import com.limito.limitedproduct.application.mapper.LimitedProductMapper;
import com.limito.limitedproduct.domain.model.ItemAmounts;
import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.repository.ProductCacheRepository;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.domain.repository.ProductOptionRepository;
import com.limito.limitedproduct.domain.repository.ProductRepository;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.presentation.dto.request.CancelReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1.ReduceStockProductRequest;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitedProductServiceV1 {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final ProductItemRepository productItemRepository;
	private final ProductCacheRepository productCacheRepository;

	@Transactional
	public CreateProductResponseV1 createProduct(Long userId, CreateProductRequestV1 request) {
		ProductRequestInfo productRequestInfo = request.product();
		ProductOption newProductOption = LimitedProductMapper.toProductOption(productRequestInfo);

		Product product = productRepository.findByNameAndSellerIdOrElseGetNull(productRequestInfo.name(), userId);

		// TODO: refactor - if/else
		if (product != null) {
			product.validateCategoryId(productRequestInfo.categoryId());
			product.validateBrandName(productRequestInfo.brandName());
		} else {
			Product newProduct = LimitedProductMapper.toProduct(userId, productRequestInfo);
			product = productRepository.save(newProduct);
		}

		newProductOption.attachProduct(product.getId());

		List<ProductItem> newProductItemList = request.productItems()
			.stream()
			.map(LimitedProductMapper::toProductItem)
			.toList();
		newProductOption.attachProductItems(newProductItemList);

		ProductOption savedProductOption = productOptionRepository.save(newProductOption);
		savedProductOption.initStatus();

		return LimitedProductMapper.toCreateProductResponse(product, savedProductOption);
	}

	public GetProductOptionResponseV1 getProductOption(UUID limitedProductOptionId) {
		ProductOption productOption = productOptionRepository.findByIdOrElseThrow(limitedProductOptionId);
		Product product = productRepository.findByIdOrElseThrow(productOption.getProductId());

		return LimitedProductMapper.toGetProductOptionResponse(product, productOption);
	}

	public GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimits(
		GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1
	) {
		List<ProductItem> productItemList = productItemRepository.findAllById(
			getPurchaseAmountLimitRequestV1.itemIdList()
				.stream()
				.toList()
		);

		return LimitedProductMapper.toGetPurchaseAmountLimitResponse(productItemList);
	}

	public void reserveStock(ReserveStockRequestV1 request) {
		List<ItemAmountRequest> itemAmountRequestList = request.items();
		List<UUID> requestItemIdList = itemAmountRequestList.stream()
			.map(ItemAmountRequest::limitedProductItemId)
			.toList();

		validateDuplicateId(requestItemIdList);

		List<ProductItem> productItemList = productItemRepository.findAllById(requestItemIdList);

		validateOpened(productItemList);
		validateIsNotSoldOut(productItemList);

		Map<UUID, ProductItem> productItemMap = productItemList.stream()
			.collect(Collectors.toMap(ProductItem::getId, Function.identity()));
		validatePurchaseAmountLimit(productItemMap, itemAmountRequestList);

		List<ItemAmountRequest> reservedItemList = new ArrayList<>();
		try {
			for (ItemAmountRequest itemAmountRequest : itemAmountRequestList) {
				productCacheRepository.reserve(itemAmountRequest.limitedProductItemId(),
					itemAmountRequest.amount());
				reservedItemList.add(itemAmountRequest);
			}
		} catch (Exception e) {
			for (ItemAmountRequest reservedItem : reservedItemList) {
				productCacheRepository.cancelReservation(reservedItem.limitedProductItemId(), reservedItem.amount());
			}
			throw e;
		}
	}

	public void cancelReserveStock(CancelReserveStockRequestV1 request) {
		ItemAmounts itemAmounts = LimitedProductMapper.toItemAmounts(request);

		itemAmounts.validateDuplicateId(
			request.items()
				.stream()
				.map(ItemAmountRequest::limitedProductItemId)
				.toList()
		);

		productCacheRepository.cancelReservations(itemAmounts);
	}

	@Transactional
	public void reduceStock(ReduceStockRequestV1 request) {
		List<ReduceStockProductRequest> reduceStockProductRequestList = request.products();
		List<UUID> requestItemIdList = reduceStockProductRequestList
			.stream()
			.map(ReduceStockProductRequest::limitedProductItemId)
			.toList();
		validateDuplicateId(requestItemIdList);

		List<ReduceStockProductRequest> reducedItemList = new ArrayList<>();
		try {
			for (ReduceStockProductRequest reduceStockProductRequest : reduceStockProductRequestList) {
				productCacheRepository.reduceStock(
					reduceStockProductRequest.limitedProductItemId(),
					reduceStockProductRequest.amount()
				);
				reducedItemList.add(reduceStockProductRequest);
			}
		} catch (Exception e) {
			for (ReduceStockProductRequest reduceStockProductRequest : reducedItemList) {
				productCacheRepository.cancelReduction(
					reduceStockProductRequest.limitedProductItemId(),
					reduceStockProductRequest.amount()
				);
			}
			throw e;
		}

		for (ReduceStockProductRequest reduceStockProductRequest : reduceStockProductRequestList) {
			if (productCacheRepository.checkSoldOut(reduceStockProductRequest.limitedProductItemId())) {
				productOptionRepository.soldOut(
					reduceStockProductRequest.limitedProductOptionId(),
					reduceStockProductRequest.limitedProductItemId()
				);
			}
		}
	}

	private void validateDuplicateId(List<UUID> requestItemIdList) {
		Set<UUID> itemIdSet = new HashSet<>(requestItemIdList);

		if (itemIdSet.size() != requestItemIdList.size()) {
			throw LimitedProductInternalException.of(LimitedProductInternalErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
		}
	}

	private void validateOpened(List<ProductItem> productItemList) {
		for (ProductItem productItem : productItemList) {
			productItem.validateProductOptionOpened();
		}
	}

	private void validateIsNotSoldOut(List<ProductItem> productItemList) {
		for (ProductItem productItem : productItemList) {
			productItem.validateProductItemIsNotSoldOut();
		}
	}

	private void validatePurchaseAmountLimit(
		Map<UUID, ProductItem> productItemList,
		List<ItemAmountRequest> itemAmountList
	) {
		for (ItemAmountRequest itemAmountRequest : itemAmountList) {
			ProductItem productItem = productItemList.get(itemAmountRequest.limitedProductItemId());
			productItem.validatePurchaseAmountLimit(itemAmountRequest.amount());
		}
	}
}
