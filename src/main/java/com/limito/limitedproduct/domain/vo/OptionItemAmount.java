package com.limito.limitedproduct.domain.vo;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionItemAmount {

	private UUID optionId;
	private UUID itemId;
	private int amount;
}
