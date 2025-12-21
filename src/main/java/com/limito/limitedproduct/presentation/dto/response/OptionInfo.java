package com.limito.limitedproduct.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionInfo {

	private String name;
	private String value;
}
