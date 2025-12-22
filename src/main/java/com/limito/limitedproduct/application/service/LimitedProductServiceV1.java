package com.limito.limitedproduct.application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.application.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.application.mapper.LimitedProductMapper;
import com.limito.limitedproduct.domain.model.ItemAmounts;
import com.limito.limitedproduct.domain.model.OptionItemAmounts;
import com.limito.limitedproduct.domain.model.Product;
import com.limito.limitedproduct.domain.model.ProductAndOption;
import com.limito.limitedproduct.domain.model.ProductItems;
import com.limito.limitedproduct.domain.model.ProductOption;
import com.limito.limitedproduct.domain.repository.ProductCacheRepository;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.domain.repository.ProductOptionQueryRepository;
import com.limito.limitedproduct.domain.repository.ProductOptionRepository;
import com.limito.limitedproduct.domain.repository.ProductRepository;
import com.limito.limitedproduct.domain.vo.OptionItemAmount;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.presentation.dto.request.CancelReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1;
import com.limito.limitedproduct.presentation.dto.request.CreateProductRequestV1.ProductRequestInfo;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.OptionItemAmountRequest;
import com.limito.limitedproduct.presentation.dto.request.ReduceStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.ReserveStockRequestV1;
import com.limito.limitedproduct.presentation.dto.request.RollbackStockRequestV1;
import com.limito.limitedproduct.presentation.dto.response.CreateProductResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetInCartProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetOrderedProductInfoResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductOptionResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetProductsByCategoryResponseV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LimitedProductServiceV1 {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final ProductItemRepository productItemRepository;

	private final ProductCacheRepository productCacheRepository;

	private final ProductOptionQueryRepository productOptionQueryRepository;

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
		Product product = productRepository.findByIdOrElseThrowAppException(productOption.getProductId());

		return LimitedProductMapper.toGetProductOptionResponse(product, productOption);
	}

	public GetProductsByCategoryResponseV1 getProductsByCategory(UUID categoryId, Pageable pageable) {
		// TODO: 카테고리 캐싱해와서 정보 가져오기
		String category = "임시 카테고리명";

		Page<ProductAndOption> productAndOptionList
			= productOptionQueryRepository.findOptionsByCategoryId(categoryId, pageable);

		return LimitedProductMapper.toGetProductsByCategoryResponse(
			categoryId,
			category,
			new PagedModel<>(productAndOptionList.map(LimitedProductMapper::toProductAndOptionResponse))
		);
	}

	public GetPurchaseAmountLimitResponseV1 getPurchaseAmountLimits(
		GetPurchaseAmountLimitRequestV1 getPurchaseAmountLimitRequestV1) {
		List<ProductItem> productItemList = productItemRepository.findAllByIdSet(
			getPurchaseAmountLimitRequestV1.itemIdList());

		return LimitedProductMapper.toGetPurchaseAmountLimitResponse(productItemList);
	}

	public void reserveStock(ReserveStockRequestV1 request) {
		List<ItemAmountRequest> itemAmountRequestList = request.items();
		List<UUID> requestItemIdList = itemAmountRequestList.stream()
			.map(ItemAmountRequest::limitedProductItemId)
			.toList();

		validateDuplicateId(requestItemIdList);

		List<ProductItem> productItemList = productItemRepository.findAllByIdSet(new HashSet<>(requestItemIdList));

		validateOpened(productItemList);
		validateIsNotSoldOut(productItemList);

		Map<UUID, ProductItem> productItemMap = productItemList.stream()
			.collect(Collectors.toMap(ProductItem::getId, Function.identity()));
		validatePurchaseAmountLimit(productItemMap, itemAmountRequestList);

		List<ItemAmountRequest> reservedItemList = new ArrayList<>();
		try {
			for (ItemAmountRequest itemAmountRequest : itemAmountRequestList) {
				productCacheRepository.reserve(itemAmountRequest.limitedProductItemId(), itemAmountRequest.amount());
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
		List<OptionItemAmountRequest> reduceStockProductRequestList = request.products();
		List<UUID> requestItemIdList = reduceStockProductRequestList
			.stream()
			.map(OptionItemAmountRequest::limitedProductItemId)
			.toList();
		validateDuplicateId(requestItemIdList);

		List<OptionItemAmountRequest> reducedItemList = new ArrayList<>();
		try {
			for (OptionItemAmountRequest reduceStockProductRequest : reduceStockProductRequestList) {
				productCacheRepository.reduceStock(
					reduceStockProductRequest.limitedProductItemId(),
					reduceStockProductRequest.amount()
				);
				reducedItemList.add(reduceStockProductRequest);
			}
		} catch (Exception e) {
			for (OptionItemAmountRequest reduceStockProductRequest : reducedItemList) {
				productCacheRepository.cancelReduction(
					reduceStockProductRequest.limitedProductItemId(),
					reduceStockProductRequest.amount()
				);
			}
			throw e;
		}

		for (OptionItemAmountRequest reduceStockProductRequest : reduceStockProductRequestList) {
			if (productCacheRepository.checkSoldOut(reduceStockProductRequest.limitedProductItemId())) {
				productOptionRepository.soldOut(reduceStockProductRequest.limitedProductOptionId(),
					reduceStockProductRequest.limitedProductItemId());
			}
		}
	}

	@Transactional
	public void rollbackStock(RollbackStockRequestV1 request) {
		OptionItemAmounts optionItemAmounts = LimitedProductMapper.toOptionItemAmounts(request);
		optionItemAmounts.validateDuplicateId(
			request.products()
				.stream()
				.map(OptionItemAmountRequest::limitedProductItemId)
				.toList()
		);

		List<ProductOption> productOptionList
			= productOptionRepository.findAllByIds(optionItemAmounts.getOptionIdSet());
		optionItemAmounts.validateOptionId(productOptionList);

		// TODO: refactor - for-if-for(삼중ㅠㅠ)
		for (OptionItemAmount optionItemAmount : optionItemAmounts.getOptionItemAmounts()) {
			if (productCacheRepository.rollbackStock(optionItemAmount.getItemId(), optionItemAmount.getAmount())) {
				for (ProductOption productOption : productOptionList) {
					productOption.rollbackStockIfMatches(
						optionItemAmount.getOptionId(),
						optionItemAmount.getItemId(),
						optionItemAmount.getAmount()
					);
				}
			}
		}
	}

	public GetOrderedProductInfoResponseV1 getOrderedProductInfo(Set<UUID> productItemIdSet) {
		ProductItems productItemList = ProductItems.of(productItemRepository.findAllByIdSet(productItemIdSet));

		List<GetOrderedProductInfoResponseV1.OrderedProductInfo> orderedProductInfoList = new ArrayList<>();
		for (ProductItem productItem : productItemList.getProductItemList()) {
			Product product
				= productRepository.findByIdOrElseThrowAppException(productItem.getProductOption().getProductId());
			orderedProductInfoList.add(
				LimitedProductMapper.toOrderedProductInfo(product, productItem.getProductOption(), productItem)
			);
		}

		return LimitedProductMapper.toGetOrderedProductInfoResponse(orderedProductInfoList);
	}

	private void validateDuplicateId(List<UUID> requestItemIdList) {
		Set<UUID> itemIdSet = new HashSet<>(requestItemIdList);

		if (itemIdSet.size() != requestItemIdList.size()) {
			throw AppException.of(LimitedProductErrorCode.PRODUCT_ITEM_DUPLICATE_UUID);
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

	public GetInCartProductInfoResponseV1 getInCartProductInfo(UUID limitedProductItemId) {
		ProductItem productItem = productItemRepository.findByIdOrElseThrowAppException(limitedProductItemId);
		Product product
			= productRepository.findByIdOrElseThrowAppException(productItem.getProductOption().getProductId());

		return LimitedProductMapper.toGetInCartProductInfoResponseV1(
			product,
			productItem.getProductOption(),
			productItem
		);
	}
}
