package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;

import com.limito.limitedproduct.presentation.dto.response.ProductResponse.ProductItemResponseInfo;
import com.limito.limitedproduct.presentation.dto.response.ProductResponse.ProductOptionResponseInfo;
import com.limito.limitedproduct.presentation.dto.response.ProductResponse.ProductResponseInfo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetProductOptionResponseV1 {

	private ProductResponseInfo productInfo;
	private ProductOptionResponseInfo productOptionInfo;
	private List<ProductItemResponseInfo> productItems;
}
