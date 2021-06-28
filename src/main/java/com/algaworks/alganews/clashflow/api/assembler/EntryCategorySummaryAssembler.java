package com.algaworks.alganews.clashflow.api.assembler;

import com.algaworks.alganews.clashflow.api.model.EntryCategorySummaryModel;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategorySummaryProjection;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EntryCategorySummaryAssembler {
	
	private final ModelMapper modelMapper;
	
	public EntryCategorySummaryModel toModel(EntryCategorySummaryProjection entryCategory) {
		return modelMapper.map(entryCategory, EntryCategorySummaryModel.class);
	}
	
	public List<EntryCategorySummaryModel> toCollectionModel(List<EntryCategorySummaryProjection> categories) {
		return categories.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
