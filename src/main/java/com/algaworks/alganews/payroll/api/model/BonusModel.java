package com.algaworks.alganews.payroll.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BonusModel {
	
	private String title;
	
	private BigDecimal amount;

}
