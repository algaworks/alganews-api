package com.algaworks.alganews.metrics.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
public class EditorMonthlyEarningsModel {
	
	private YearMonth yearMonth;
	private BigDecimal totalAmount;
	private BigDecimal totalPlatformAverageAmount;
	
}
