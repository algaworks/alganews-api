package com.algaworks.alganews.payroll.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentEarningsModel {
	
	private Integer words;
	
	private BigDecimal totalAmount;

}
