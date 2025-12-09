package com.limito.limitedproduct.domain.vo;

import java.util.Objects;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemAmount {

	private UUID itemId;
	private int amount;

	@Override
	public int hashCode() {
		return Objects.hashCode(itemId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		ItemAmount other = (ItemAmount)obj;
		return itemId.equals(other.itemId);
	}
}
