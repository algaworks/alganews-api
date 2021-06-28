package com.algaworks.alganews.clashflow.api.assembler;

import com.algaworks.alganews.clashflow.api.model.EntryCategoryDetailedModel;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategoryDetailedProjection;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EntryCategoryDetailedAssembler {
	
	private final ModelMapper modelMapper;
	
	public EntryCategoryDetailedModel toModel(EntryCategory entryCategory) {
		return modelMapper.map(entryCategory, EntryCategoryDetailedModel.class);
	}
	
	public EntryCategoryDetailedModel toModel(EntryCategoryDetailedProjection entryCategory) {
		return modelMapper.map(entryCategory, EntryCategoryDetailedModel.class);
	}
	
}
