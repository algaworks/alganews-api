package com.algaworks.alganews.clashflow.api.model;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EntrySummaryModel {
	
	private Long id;
	
	private EntryType type;
	
	private LocalDate transactedOn;
	
	private BigDecimal amount;
	
	private EntryCategoryMinimalModel category;
	
	private String description;
	
	private boolean systemGenerated;
	
	private boolean canBeDeleted;
	
	private boolean canBeEdited;
	
}
