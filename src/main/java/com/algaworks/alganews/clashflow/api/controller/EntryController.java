package com.algaworks.alganews.clashflow.api.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.algaworks.alganews.clashflow.domain.exception.EntryCategoryNotFoundException;
import com.algaworks.alganews.common.domain.BusinessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.clashflow.api.assembler.CashFlowEntryDetailedAssembler;
import com.algaworks.alganews.clashflow.api.assembler.CashFlowEntryInputDisassembler;
import com.algaworks.alganews.clashflow.api.assembler.CashFlowEntrySummaryAssembler;
import com.algaworks.alganews.clashflow.api.model.CashFlowEntryInput;
import com.algaworks.alganews.clashflow.api.model.EntryDetailedModel;
import com.algaworks.alganews.clashflow.api.model.EntrySummaryModel;
import com.algaworks.alganews.clashflow.domain.filter.EntryFilter;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;
import com.algaworks.alganews.clashflow.domain.repository.CashFlowEntryRepository;
import com.algaworks.alganews.clashflow.domain.repository.EntrySpecs;
import com.algaworks.alganews.clashflow.domain.service.CashFlowEntryCrudService;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cashflow/entries")
@AllArgsConstructor
@Validated
public class EntryController {
	
	private final CashFlowEntryRepository entryRepository;
	private final CashFlowEntryCrudService entryCrudService;
	
	private final CashFlowEntrySummaryAssembler entrySummaryAssembler;
	private final CashFlowEntryDetailedAssembler entryDetailedAssembler;
	
	private final CashFlowEntryInputDisassembler entryInputDisassembler;
	
	@CheckSecurity.CashFlow.Entry.CanList
	@GetMapping
	public List<EntrySummaryModel> search(@Valid EntryFilter filter,
			@SortDefault(value = "createdAt", direction = Sort.Direction.DESC) Sort sort) {
		List<CashFlowEntry> cashFlowEntries = entryRepository.findAll(EntrySpecs.usingFilter(filter), sort);
		return entrySummaryAssembler.toCollectionModel(cashFlowEntries);
	}
	
	@CheckSecurity.CashFlow.Entry.CanCreateOrEdit
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntryDetailedModel create(@Valid @RequestBody CashFlowEntryInput entryInput) {
		CashFlowEntry cashFlowEntry = entryInputDisassembler.toDomainObject(entryInput);
		
		try {
			cashFlowEntry = entryCrudService.save(cashFlowEntry);
		} catch (EntryCategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		return entryDetailedAssembler.toModel(cashFlowEntry);
	}
	
	@CheckSecurity.CashFlow.Entry.CanCreateOrEdit
	@PutMapping("/{entryId}")
	public EntryDetailedModel update(@PathVariable Long entryId, @Valid @RequestBody CashFlowEntryInput entryInput) {
		CashFlowEntry cashFlowEntry = entryCrudService.findByIdOrFail(entryId);
		entryInputDisassembler.copyToDomainObject(entryInput, cashFlowEntry);
		
		try {
			cashFlowEntry = entryCrudService.save(cashFlowEntry);
		} catch (EntryCategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		return entryDetailedAssembler.toModel(cashFlowEntry);
	}

	@CheckSecurity.CashFlow.Entry.CanList
	@GetMapping("/{entryId}")
	public EntryDetailedModel findById(@PathVariable Long entryId) {
		CashFlowEntry cashFlowEntry = entryCrudService.findByIdOrFail(entryId);
		return entryDetailedAssembler.toModel(cashFlowEntry);
	}

	@CheckSecurity.CashFlow.Entry.CanCreateOrEdit
	@DeleteMapping("/{entryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long entryId) {
		entryCrudService.delete(entryId);
	}
	
	@CheckSecurity.CashFlow.Entry.CanCreateOrEdit
	@PutMapping("/bulk-removals")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAll(@RequestBody @Valid @NotEmpty Set<Long> entryIds) {
		entryCrudService.deleteAll(entryIds);
	}
	
}
