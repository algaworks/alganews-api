package com.algaworks.alganews.metrics.api.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditorMonthlyEarningsProjection {
	
	private String yearMonthValue;
	private BigDecimal totalAmount;
	private BigDecimal totalPlatformAverageAmount;
	
	public static EditorMonthlyEarningsProjection of(YearMonth yearMonth) {
		return new EditorMonthlyEarningsProjection(yearMonth.toString(), BigDecimal.ZERO, BigDecimal.ZERO);
	}
	
	public YearMonth getYearMonth() {
		return YearMonth.parse(this.getYearMonthValue(), DateTimeFormatter.ofPattern("yyyy-MM"));
	}
}
