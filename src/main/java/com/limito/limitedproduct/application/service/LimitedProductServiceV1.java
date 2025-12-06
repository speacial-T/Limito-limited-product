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

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.ProductCacheRepository;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1.ReduceStockProduct;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.StockItem;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitedProductServiceV1 {

	private final ProductItemRepository productItemRepository;
	private final ProductCacheRepository productCacheRepository;

	public GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimits(
		GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1
	) {
		List<ProductItem> productItemList = productItemRepository.findAllById(
			getPurchaseAmountLimitRequestV1.itemIdList()
				.stream()
				.toList()
		);

		return GetPurchaseAmountLimitResponseV1.of(productItemList);
	}

	public void reserveStock(ReserveStockRequestV1 request) {
		List<UUID> requestItemIdList = request.items()
			.stream()
			.map(StockItem::limitedProductItemId)
			.toList();

		validateDuplicateId(requestItemIdList);

		List<ProductItem> productItemList = productItemRepository.findAllById(requestItemIdList);

		validateOpened(productItemList);
		validateIsNotSoldOut(productItemList);

		Map<UUID, ProductItem> productItemMap = productItemList.stream()
			.collect(Collectors.toMap(ProductItem::getId, Function.identity()));
		validatePurchaseAmountLimit(productItemMap, request.items());

		List<StockItem> reservedItemList = new ArrayList<>();
		try {
			for (StockItem stockItem : request.items()) {
				productCacheRepository.reserve(stockItem.limitedProductItemId(), stockItem.amount());
				reservedItemList.add(stockItem);
			}
		} catch (Exception e) {
			for (StockItem itemAmount : reservedItemList) {
				productCacheRepository.cancelReservation(itemAmount.limitedProductItemId(), itemAmount.amount());
			}
			throw e;
		}
	}

	public void reduceStock(ReduceStockRequestV1 request) {
		List<UUID> requestOptionIdList = request.products()
			.stream()
			.map(ReduceStockProduct::limitedProductOptionId)
			.toList();
		validateDuplicateId(requestOptionIdList);

		for (ReduceStockProduct product : request.products()) {
			List<UUID> requestItemIdList = product.items()
				.stream()
				.map(StockItem::limitedProductItemId)
				.toList();
			validateDuplicateId(requestItemIdList);
		}

		List<StockItem> reducedItemList = new ArrayList<>();
		try {
			for (ReduceStockProduct reduceStockProduct : request.products()) {
				for (StockItem stockItem : reduceStockProduct.items()) {
					productCacheRepository.reduceStock(stockItem.limitedProductItemId(), stockItem.amount());
					reducedItemList.add(stockItem);
				}
			}
		} catch (Exception e) {
			for (StockItem stockItem : reducedItemList) {
				productCacheRepository.cancelReduction(stockItem.limitedProductItemId(), stockItem.amount());
			}
			throw e;
		}

		for (ReduceStockProduct reduceStockProduct : request.products()) {
			for (StockItem stockItem : reduceStockProduct.items()) {
				if (productCacheRepository.checkSoldOut(stockItem.limitedProductItemId())) {
					productItemRepository.soldOut(stockItem.limitedProductItemId());
				}
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

	private void validatePurchaseAmountLimit(Map<UUID, ProductItem> productItemList, List<StockItem> itemAmountList) {
		for (StockItem itemAmount : itemAmountList) {
			ProductItem productItem = productItemList.get(itemAmount.limitedProductItemId());
			productItem.validatePurchaseAmountLimit(itemAmount.amount());
		}
	}
}
