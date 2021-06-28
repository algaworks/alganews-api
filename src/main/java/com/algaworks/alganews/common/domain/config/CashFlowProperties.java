package com.algaworks.alganews.common.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("alganews.cashflow")
public class CashFlowProperties {
	
	@NotNull
	private String defaultCategoryName;
	
}