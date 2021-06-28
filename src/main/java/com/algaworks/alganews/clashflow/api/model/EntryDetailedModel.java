package com.algaworks.alganews.clashflow.api.model;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.users.api.model.UserMinimalModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
public class EntryDetailedModel {
	
	private Long id;
	
	private EntryType type;
	
	private LocalDate transactedOn;
	
	private BigDecimal amount;
	
	private EntryCategoryMinimalModel category;
	
	private String description;
	
	private boolean systemGenerated;
	
	private UserMinimalModel createdBy;
	
	private OffsetDateTime createdAt;
	
	private UserMinimalModel updatedBy;
	
	private OffsetDateTime updatedAt;
	
	private boolean canBeDeleted;
	
	private boolean canBeEdited;
	
}
