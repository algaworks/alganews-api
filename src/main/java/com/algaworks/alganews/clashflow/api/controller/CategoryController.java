package com.algaworks.alganews.clashflow.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.alganews.clashflow.api.assembler.EntryCategorySummaryAssembler;
import com.algaworks.alganews.clashflow.api.model.EntryCategorySummaryModel;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategoryDetailedProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.clashflow.api.assembler.EntryCategoryDetailedAssembler;
import com.algaworks.alganews.clashflow.api.assembler.EntryCategoryInputDisassembler;
import com.algaworks.alganews.clashflow.api.model.EntryCategoryInput;
import com.algaworks.alganews.clashflow.api.model.EntryCategoryDetailedModel;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.clashflow.domain.repository.EntryCategoryRepository;
import com.algaworks.alganews.clashflow.domain.service.EntryCategoryCrudService;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cashflow/categories")
@AllArgsConstructor
public class CategoryController {
	
	private final EntryCategoryRepository entryCategoryRepository;
	private final EntryCategoryCrudService entryCategoryCrudService;
	
	private final EntryCategoryDetailedAssembler categoryDetailedAssembler;
	private final EntryCategorySummaryAssembler categorySummaryAssembler;
	private final EntryCategoryInputDisassembler entryCategoryInputDisassembler;
	
	@CheckSecurity.CashFlow.Category.CanList
	@GetMapping
	public List<EntryCategorySummaryModel> search(@RequestParam(required = false) EntryType type,
			@SortDefault(value = "createdAt", direction = Sort.Direction.DESC) Sort sort) {
		return categorySummaryAssembler.toCollectionModel(entryCategoryRepository.findAllAsProjectionByType(type, sort));
	}
	
	@CheckSecurity.CashFlow.Category.CanList
	@GetMapping("/{entryCategoryId}")
	public EntryCategoryDetailedModel findById(@PathVariable Long entryCategoryId) {
		EntryCategoryDetailedProjection entryCategory = entryCategoryCrudService.findOneByIdAsProjectionOrFail(entryCategoryId);
		return categoryDetailedAssembler.toModel(entryCategory);
	}
	
	@CheckSecurity.CashFlow.Category.CanCreateOrEdit
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntryCategoryDetailedModel create(@Valid @RequestBody EntryCategoryInput entryCategoryInput) {
		EntryCategory entryCategory = entryCategoryInputDisassembler.toDomainObject(entryCategoryInput);
		
		entryCategoryCrudService.save(entryCategory);
		
		EntryCategoryDetailedProjection projection = entryCategoryCrudService.findOneByIdAsProjectionOrFail(entryCategory.getId());
		return categoryDetailedAssembler.toModel(projection);
	}
	
	@CheckSecurity.CashFlow.Category.CanCreateOrEdit
	@PutMapping("/{entryCategoryId}")
	public EntryCategoryDetailedModel update(@PathVariable Long entryCategoryId, @Valid @RequestBody EntryCategoryInput entryCategoryInput) {
		EntryCategory entryCategory = entryCategoryCrudService.findByIdOrFail(entryCategoryId);
		
		entryCategoryInputDisassembler.copyToDomainObject(entryCategoryInput, entryCategory);
		entryCategoryCrudService.save(entryCategory);
		
		EntryCategoryDetailedProjection projection = entryCategoryCrudService.findOneByIdAsProjectionOrFail(entryCategoryId);
		return categoryDetailedAssembler.toModel(projection);
	}

	@CheckSecurity.CashFlow.Category.CanCreateOrEdit
	@DeleteMapping("/{entryCategoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long entryCategoryId) {
		entryCategoryCrudService.delete(entryCategoryId);
	}
	
}
