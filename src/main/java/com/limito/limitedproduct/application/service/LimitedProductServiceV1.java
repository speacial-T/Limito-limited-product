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

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.domain.repository.ProductCacheRepository;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.domain.repository.ProductOptionRepository;
import com.limito.limitedproduct.global.exception.LimitedProductInternalErrorCode;
import com.limito.limitedproduct.global.exception.LimitedProductInternalException;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1.ReduceStockProductRequest;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1.ReserveStockItemRequest;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LimitedProductServiceV1 {

	private final ProductOptionRepository productOptionRepository;
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
		List<ReserveStockItemRequest> reserveStockItemRequestList = request.items();
		List<UUID> requestItemIdList = reserveStockItemRequestList.stream()
			.map(ReserveStockItemRequest::limitedProductItemId)
			.toList();

		validateDuplicateId(requestItemIdList);

		List<ProductItem> productItemList = productItemRepository.findAllById(requestItemIdList);

		validateOpened(productItemList);
		validateIsNotSoldOut(productItemList);

		Map<UUID, ProductItem> productItemMap = productItemList.stream()
			.collect(Collectors.toMap(ProductItem::getId, Function.identity()));
		validatePurchaseAmountLimit(productItemMap, reserveStockItemRequestList);

		List<ReserveStockItemRequest> reservedItemList = new ArrayList<>();
		try {
			for (ReserveStockItemRequest reserveStockItemRequest : reserveStockItemRequestList) {
				productCacheRepository.reserve(reserveStockItemRequest.limitedProductItemId(),
					reserveStockItemRequest.amount());
				reservedItemList.add(reserveStockItemRequest);
			}
		} catch (Exception e) {
			for (ReserveStockItemRequest reservedItem : reservedItemList) {
				productCacheRepository.cancelReservation(reservedItem.limitedProductItemId(), reservedItem.amount());
			}
			throw e;
		}
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
		List<ReserveStockItemRequest> reserveStockItemRequestList
	) {
		for (ReserveStockItemRequest reserveStockItemRequest : reserveStockItemRequestList) {
			ProductItem productItem = productItemList.get(reserveStockItemRequest.limitedProductItemId());
			productItem.validatePurchaseAmountLimit(reserveStockItemRequest.amount());
		}
	}
}
