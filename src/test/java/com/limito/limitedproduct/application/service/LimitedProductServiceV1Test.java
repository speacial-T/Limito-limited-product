package com.limito.limitedproduct.application.service;

import static com.limito.limitedproduct.application.service.ItemFixtureGenerator.*;
import static com.limito.limitedproduct.application.service.LimitedProductServiceV1TestHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.limito.common.exception.AppException;
import com.limito.limitedproduct.domain.repository.ProductItemRepository;
import com.limito.limitedproduct.global.exception.LimitedProductErrorCode;
import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

@DisplayName("Service:LimitedProduct")
@ExtendWith(MockitoExtension.class)
class LimitedProductServiceV1Test {

	@Mock
	private ProductItemRepository productItemRepository;
	@InjectMocks
	private LimitedProductServiceV1 limitedProductServiceV1;

	private void 상품_아이디가_주어지면_준비된_상품을_반환한다() {
		when(productItemRepository.findAllById(correctItemIdList))
			.thenReturn(itemList);
	}

	private GetPurchaseAmountLimitResponseV1 상품_아이디목록을_이용해서_상품을_조회한다() {
		return limitedProductServiceV1.getPurchaseAmountLimits(correctRequest);
	}

	private void 상품_조회_결과를_검증한다(GetPurchaseAmountLimitResponseV1 response) {
		assertionFields(response);
		verify(productItemRepository, times(1))
			.findAllById(correctItemIdList);
	}

	private void 잘못된_상품_아이디가_주어지면_에러가_발생한다() {
		when(productItemRepository.findAllById(wrongUUIDItemIdList))
			.thenThrow(
				AppException.of(
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getStatus(),
					LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage()
				)
			);
	}

	private void 예상한_예외가_발생했는지_검증한다() {
		assertThatThrownBy(() -> limitedProductServiceV1.getPurchaseAmountLimits(wrongUUIDRequest))
			.isInstanceOf(AppException.class)
			.hasMessageContaining(LimitedProductErrorCode.PRODUCT_ITEM_NOT_FOUND.getMessage());
		verify(productItemRepository, times(1)).findAllById(wrongUUIDItemIdList);
	}

	@Nested
	@DisplayName("[성공] 인당 최대 구매 가능 수량 조회")
	class LimitedProductSuccessTest {
		@DisplayName("성공")
		@Test
		void getPurchaseAmountLimits_Success() {
			상품_아이디가_주어지면_준비된_상품을_반환한다();
			var response = 상품_아이디목록을_이용해서_상품을_조회한다();
			상품_조회_결과를_검증한다(response);
		}

	}

	@Nested
	@DisplayName("[실패] 인당 최대 구매 가능 수량 조회")
	class LimitedProductFailTest {
		@DisplayName("실패 - 잘못된 uuid")
		@Test
		void getPurchaseAmountLimits_Fail_whenWrongUuid_thenThrowException() {
			잘못된_상품_아이디가_주어지면_에러가_발생한다();
			예상한_예외가_발생했는지_검증한다();
		}

	}
}
