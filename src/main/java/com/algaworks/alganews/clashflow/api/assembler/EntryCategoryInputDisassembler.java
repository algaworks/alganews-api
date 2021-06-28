package com.algaworks.alganews.clashflow.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.clashflow.api.model.EntryCategoryInput;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EntryCategoryInputDisassembler {

	private final ModelMapper modelMapper;
	
	public EntryCategory toDomainObject(EntryCategoryInput categoryInput) {
		return modelMapper.map(categoryInput, EntryCategory.class);
	}
	
	public void copyToDomainObject(EntryCategoryInput categoryInput, EntryCategory category) {
		modelMapper.map(categoryInput, category);
	}
	
}
