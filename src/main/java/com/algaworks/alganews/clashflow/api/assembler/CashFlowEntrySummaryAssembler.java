package com.algaworks.alganews.clashflow.api.assembler;

import com.algaworks.alganews.clashflow.api.model.EntrySummaryModel;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CashFlowEntrySummaryAssembler {
	
	private final ModelMapper modelMapper;
	
	public EntrySummaryModel toModel(CashFlowEntry entry) {
		return modelMapper.map(entry, EntrySummaryModel.class);
	}
	
	public List<EntrySummaryModel> toCollectionModel(List<CashFlowEntry> entries) {
		return entries.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
