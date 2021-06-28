package com.algaworks.alganews.metrics.api.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRevenueExpenseProjection {
	
	private String yearMonthValue;
	
	private BigDecimal totalExpenses;
	private BigDecimal totalRevenues;
	
	public static MonthlyRevenueExpenseProjection of(YearMonth yearMonth) {
		return new MonthlyRevenueExpenseProjection(yearMonth.toString(), BigDecimal.ZERO, BigDecimal.ZERO);
	}
	
	public YearMonth getYearMonth() {
		return YearMonth.parse(this.getYearMonthValue(), DateTimeFormatter.ofPattern("yyyy-MM"));
	}
	
	public Month getMonth() {
		return getYearMonth().getMonth();
	}
	
	public int getYear() {
		return getYearMonth().getYear();
	}
	
}
