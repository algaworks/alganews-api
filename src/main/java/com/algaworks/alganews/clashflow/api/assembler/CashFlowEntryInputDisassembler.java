package com.algaworks.alganews.clashflow.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.clashflow.api.model.CashFlowEntryInput;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CashFlowEntryInputDisassembler {
	
	private final ModelMapper modelMapper;
	
	public CashFlowEntry toDomainObject(CashFlowEntryInput entryInput) {
		return modelMapper.map(entryInput, CashFlowEntry.class);
	}
	
	public void copyToDomainObject(CashFlowEntryInput entryInput, CashFlowEntry entry) {
		modelMapper.map(entryInput, entry);
	}
	
}
