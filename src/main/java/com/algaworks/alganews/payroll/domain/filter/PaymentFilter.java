package com.algaworks.alganews.payroll.domain.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class PaymentFilter {
	
	private Long payeeId;
	private String payeeEmail;
	private YearMonth scheduledToYearMonth;
	
}
