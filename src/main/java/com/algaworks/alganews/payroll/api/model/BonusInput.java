package com.algaworks.alganews.payroll.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class BonusInput {
	
	@NotBlank
	@Size(max = 50)
	private String title;
	
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;

}
