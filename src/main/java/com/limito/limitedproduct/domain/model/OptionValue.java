package com.limito.limitedproduct.domain.model;

import java.util.UUID;

import com.limito.common.security.audit.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_option_values")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OptionValue extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "option_value_id", nullable = false, updatable = false)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "option_id", nullable = false, updatable = false)
	private Option option;

	@Column(name = "value", nullable = false, updatable = false)
	private String value;

	@Builder
	private OptionValue(Option option, String value) {
		this.option = option;
		this.value = value;
	}
}
