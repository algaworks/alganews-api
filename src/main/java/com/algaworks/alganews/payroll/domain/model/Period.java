package com.algaworks.alganews.payroll.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Period {
	
	@Column(name = "period_starts_on")
	private LocalDate startsOn;
	
	@Column(name = "period_ends_on")
	private LocalDate endsOn;
	
}