package com.algaworks.alganews.clashflow.domain.filter;

import java.time.YearMonth;

import javax.validation.constraints.NotNull;

import com.algaworks.alganews.clashflow.domain.model.EntryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryFilter {
	
	@NotNull
	private EntryType type;
	
	@NotNull
	private YearMonth yearMonth;
	
}
