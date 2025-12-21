package com.limito.limitedproduct.domain.model;

import java.util.UUID;

import com.limito.common.security.audit.BaseEntity;
import com.limito.limitedproduct.domain.vo.OptionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_limited_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Option extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "option_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "name", nullable = false, updatable = false)
	private String name;

	@Column(name = "type", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private OptionType type;

	@Builder
	private Option(String name, OptionType optionType) {
		this.name = name;
		this.type = optionType;
	}
}
