package com.limito.limitedproduct.application.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;

// fixture : 테스트에 사용할 값이 고정된 객체
public class ItemFixtureGenerator {
	public static final UUID itemId1 = UUID.fromString("40000000-0000-0000-0000-000000000001");
	public static final UUID itemId2 = UUID.fromString("40000000-0000-0000-0000-000000000002");
	public static final UUID wrongItemId1 = UUID.fromString("40000000-1111-0000-0000-000000000001");

	public static final Set<UUID> correctItemIdSet = Set.of(itemId1, itemId2);
	public static final List<UUID> correctItemIdList = correctItemIdSet.stream().toList();
	public static final GetPurchaseAmountLimitRequestV1 correctRequest = new GetPurchaseAmountLimitRequestV1(
		correctItemIdSet);

	public static final Set<UUID> wrongUUIDItemIdSet = Set.of(wrongItemId1);
	public static final List<UUID> wrongUUIDItemIdList = wrongUUIDItemIdSet.stream().toList();
	public static final GetPurchaseAmountLimitRequestV1 wrongUUIDRequest
		= new GetPurchaseAmountLimitRequestV1(wrongUUIDItemIdSet);

	public static final List<ProductItem> itemList = List.of(createFirstFixture(), createSecondFixture());

	public static ProductItem createFirstFixture() {
		return ProductItem.builder()
			.id(itemId1)
			.size("M")
			.price(10000)
			.stock(10)
			.isSoldOut(false)
			.purchaseAmountLimit(3)
			.build();
	}

	public static ProductItem createSecondFixture() {
		return ProductItem.builder()
			.id(itemId2)
			.size("L")
			.price(12000)
			.stock(5)
			.isSoldOut(false)
			.purchaseAmountLimit(2)
			.build();
	}
}
