package com.algaworks.alganews.payroll.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Bonus {
	
	private String title;
	
	private BigDecimal amount;

}
