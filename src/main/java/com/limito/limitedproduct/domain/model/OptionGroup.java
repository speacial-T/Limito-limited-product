package com.limito.limitedproduct.domain.model;

import java.util.List;
import java.util.UUID;

import com.limito.common.security.audit.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OptionGroup extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "limited_option_group_id", nullable = false, updatable = false)
	private UUID id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OptionValue> optionValueList;

	@Builder
	private OptionGroup(
		List<OptionValue> optionValueList
	) {
		this.optionValueList = optionValueList;
	}
}
