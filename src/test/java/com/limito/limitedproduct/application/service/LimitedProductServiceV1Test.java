package com.limito.limitedproduct.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.domain.repository.LimitedProductItemRepository;
import com.limito.limitedproduct.domain.vo.ProductItem;
import com.limito.limitedproduct.global.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.presentation.dto.request.GetPurchaseAmountLimitRequestV1;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

@ExtendWith(MockitoExtension.class)
class LimitedProductServiceV1Test {

	@Mock
	private LimitedProductItemRepository limitedProductItemRepository;

	@InjectMocks
	private LimitedProductServiceV1 limitedProductServiceV1;

	@DisplayName("인당 구매 가능 수량 조회 - 성공")
	@Test
	void getPurchaseAmountLimits_Success() {
		// given
		UUID itemId1 = UUID.fromString("40000000-0000-0000-0000-000000000001");
		UUID itemId2 = UUID.fromString("40000000-0000-0000-0000-000000000002");

		GetPurchaseAmountLimitRequestV1 request = new GetPurchaseAmountLimitRequestV1(
			List.of(itemId1, itemId2)
		);

		ProductItem productItem1 = ProductItem.builder()
			.id(itemId1)
			.size("M")
			.price(10000)
			.stock(10)
			.isSoldOut(false)
			.purchaseAmountLimit(3)
			.build();

		ProductItem productItem2 = ProductItem.builder()
			.id(itemId2)
			.size("L")
			.price(12000)
			.stock(5)
			.isSoldOut(false)
			.purchaseAmountLimit(2)
			.build();

		when(limitedProductItemRepository.findById(itemId1)).thenReturn(productItem1);
		when(limitedProductItemRepository.findById(itemId2)).thenReturn(productItem2);

		// when
		GetPurchaseAmountLimitResponseV1 response = limitedProductServiceV1.getPurchaseAmountLimits(request);

		// then
		assertThat(response).isNotNull();
		assertThat(response.getItems()).hasSize(2);

		GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit firstItem = response.getItems().get(0);
		GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit secondItem = response.getItems().get(1);

		assertThat(firstItem.getLimitedProductItemId()).isEqualTo(itemId1);
		assertThat(firstItem.getPurchaseAmountLimit()).isEqualTo(3);

		assertThat(secondItem.getLimitedProductItemId()).isEqualTo(itemId2);
		assertThat(secondItem.getPurchaseAmountLimit()).isEqualTo(2);

		verify(limitedProductItemRepository, times(1)).findById(itemId1);
		verify(limitedProductItemRepository, times(1)).findById(itemId2);
	}

	@DisplayName("인당 구매 가능 수량 조회 - 실패(잘못된 uuid)")
	@Test
	void getPurchaseAmountLimits_Fail_whenWrongUuid_thenThrowException() {
		// given
		UUID wrongItemId1 = UUID.fromString("40000000-1111-0000-0000-000000000001");

		GetPurchaseAmountLimitRequestV1 requestWrongItemId1 = new GetPurchaseAmountLimitRequestV1(
			List.of(wrongItemId1)
		);

		when(limitedProductItemRepository.findById(wrongItemId1))
			.thenThrow(
				AppException.of(
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getStatus(),
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage()
				)
			);

		// when & then
		assertThatThrownBy(() -> limitedProductServiceV1.getPurchaseAmountLimits(requestWrongItemId1))
			.isInstanceOf(AppException.class)
			.hasMessageContaining(LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage());

		verify(limitedProductItemRepository, times(1)).findById(wrongItemId1);
	}
}
