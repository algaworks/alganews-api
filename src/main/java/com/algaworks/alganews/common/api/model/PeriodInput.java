package com.algaworks.alganews.common.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PeriodInput {
	
	@NotNull
	private LocalDate startsOn;
	
	@NotNull
	private LocalDate endsOn;
	
}
