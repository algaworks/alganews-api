package com.algaworks.alganews.clashflow.api.assembler;

import com.algaworks.alganews.clashflow.api.model.EntryDetailedModel;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;

import com.algaworks.alganews.users.api.assembler.UserMinimalModelAssembler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CashFlowEntryDetailedAssembler {
	
	private final ModelMapper modelMapper;
	
	private final UserMinimalModelAssembler userMinimalModelAssembler;
	
	public EntryDetailedModel toModel(CashFlowEntry cashFlowEntry) {
		EntryDetailedModel entryDetailedModel = modelMapper.map(cashFlowEntry, EntryDetailedModel.class);
		entryDetailedModel.setCreatedBy(userMinimalModelAssembler.toModel(cashFlowEntry.getCreatedBy()));
		return entryDetailedModel;
	}
	
}
