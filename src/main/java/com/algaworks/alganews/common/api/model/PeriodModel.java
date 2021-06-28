package com.algaworks.alganews.common.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeriodModel {
	
	private LocalDate startsOn;
	
	private LocalDate endsOn;
	
}
