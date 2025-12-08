package com.limito.limitedproduct.domain.vo;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemAmount {

	private UUID itemId;
	private int amount;
}
