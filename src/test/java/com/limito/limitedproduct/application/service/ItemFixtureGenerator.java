package com.limito.limitedproduct.application.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.limito.limitedproduct.domain.model.ProductItem;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;

// fixture : 테스트에 사용할 값이 고정된 객체
public class ItemFixtureGenerator {
	public static UUID itemId1 = UUID.fromString("40000000-0000-0000-0000-000000000001");
	public static UUID itemId2 = UUID.fromString("40000000-0000-0000-0000-000000000002");
	public static UUID wrongItemId1 = UUID.fromString("40000000-1111-0000-0000-000000000001");

	public static Set<UUID> correctItemIdSet = Set.of(itemId1, itemId2);
	public static List<UUID> correctItemIdList = correctItemIdSet.stream().toList();
	public static GetPurchaseAmountLimitRequestV1 correctRequest = new GetPurchaseAmountLimitRequestV1(
		correctItemIdSet);

	public static Set<UUID> wrongUUIDItemIdSet = Set.of(wrongItemId1);
	public static List<UUID> wrongUUIDItemIdList = wrongUUIDItemIdSet.stream().toList();
	public static GetPurchaseAmountLimitRequestV1 wrongUUIDRequest
		= new GetPurchaseAmountLimitRequestV1(wrongUUIDItemIdSet);

	public static List<ProductItem> itemList = List.of(createFirstFixture(), createSecondFixture());

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
