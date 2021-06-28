package com.algaworks.alganews.users.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserMetricsModel {
	
	private BigDecimal weeklyEarnings;
	private BigDecimal monthlyEarnings;
	private BigDecimal lifetimeEarnings;
	
	private int weeklyWords;
	private int monthlyWords;
	private int lifetimeWords;
	
}
