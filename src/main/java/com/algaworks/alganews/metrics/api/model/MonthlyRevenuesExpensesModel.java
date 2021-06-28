package com.algaworks.alganews.metrics.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyRevenuesExpensesModel {
	
	private YearMonth yearMonth;
	private BigDecimal totalRevenues;
	private BigDecimal totalExpenses;

}
