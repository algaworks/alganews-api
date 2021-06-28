package com.algaworks.alganews.clashflow.api.model;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryCategoryMinimalModel {
	
	private Long id;
	
	private String name;
	
	private EntryType type;
	
}
