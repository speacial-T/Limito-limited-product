package com.limito.limitedproduct.presentation.dto.response;

import java.util.List;

import com.limito.limitedproduct.global.dto.response.ProductItemResponseInfo;
import com.limito.limitedproduct.global.dto.response.ProductOptionResponseInfo;
import com.limito.limitedproduct.global.dto.response.ProductResponseInfo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateProductResponseV1 {

	private ProductResponseInfo productInfo;
	private ProductOptionResponseInfo productOptionInfo;
	private List<ProductItemResponseInfo> productItems;
}
