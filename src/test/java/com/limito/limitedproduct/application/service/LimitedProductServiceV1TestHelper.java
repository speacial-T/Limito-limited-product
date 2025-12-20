package com.limito.limitedproduct.application.service;

import static com.limito.limitedproduct.application.service.ItemFixtureGenerator.*;
import static org.assertj.core.api.Assertions.*;

import com.limito.limitedproduct.presentation.dto.response.GetPurchaseAmountLimitResponseV1;

public class LimitedProductServiceV1TestHelper {

	public static void assertionFields(GetPurchaseAmountLimitResponseV1 response) {
		assertThat(response).isNotNull();
		assertThat(response.getItems()).hasSize(2);

		GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit firstItem = response.getItems().get(0);
		GetPurchaseAmountLimitResponseV1.PurchaseAmountLimit secondItem = response.getItems().get(1);

		assertThat(firstItem.getLimitedProductItemId()).isEqualTo(itemId1);
		assertThat(firstItem.getPurchaseAmountLimit()).isEqualTo(3);

		assertThat(secondItem.getLimitedProductItemId()).isEqualTo(itemId2);
		assertThat(secondItem.getPurchaseAmountLimit()).isEqualTo(2);
	}
}