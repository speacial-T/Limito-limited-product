package com.limito.limitedproduct.presentation.dto.response;

import java.util.UUID;

import org.springframework.data.web.PagedModel;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetProductsByCategoryResponseV1 {

	private UUID categoryId;
	private String category;
	private PagedModel<ProductAndOptionResponse> data;
}
