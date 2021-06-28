package com.algaworks.alganews.clashflow.api.model;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.users.api.model.UserMinimalModel;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class EntryCategoryDetailedModel {
	
	private Long id;
	
	private String name;
	
	private EntryType type;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;
	
	private UserMinimalModel updatedBy;

	private UserMinimalModel createdBy;
	
	private boolean canBeDeleted;
	
	private long totalEntries;
	
}
