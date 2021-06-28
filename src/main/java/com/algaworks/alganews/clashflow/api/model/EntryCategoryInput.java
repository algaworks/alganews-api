package com.algaworks.alganews.clashflow.api.model;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EntryCategoryInput {
	
	@NotBlank
	@Size(max = 50)
	private String name;
	
	@NotNull
	private EntryType type;
	
}
