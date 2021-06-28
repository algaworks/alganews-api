package com.algaworks.alganews.clashflow.domain.service;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategoryDetailedProjection;
import com.algaworks.alganews.common.domain.config.CashFlowProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.alganews.clashflow.domain.exception.EntryCategoryNotFoundException;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.clashflow.domain.repository.CashFlowEntryRepository;
import com.algaworks.alganews.clashflow.domain.repository.EntryCategoryRepository;
import com.algaworks.alganews.common.domain.BusinessException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntryCategoryCrudService {
	
	private final EntryCategoryRepository categoryRepository;
	private final CashFlowEntryRepository entryRepository;
	private final CashFlowProperties cashFlowProperties;
	
	public EntryCategory findByIdOrFail(Long categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new EntryCategoryNotFoundException(categoryId));
	}
	
	public EntryCategoryDetailedProjection findOneByIdAsProjectionOrFail(Long categoryId) {
		return categoryRepository.findOneByIdAsProjection(categoryId)
				.orElseThrow(() -> new EntryCategoryNotFoundException(categoryId));
	}
	
	@Transactional
	public EntryCategory save(EntryCategory entryCategory) {
		categoryRepository.detach(entryCategory);
		
		validateCategoryNameAvailability(entryCategory);
		
		return categoryRepository.save(entryCategory);
	}
	
	private void validateCategoryNameAvailability(EntryCategory entryCategory) {
		categoryRepository.findOneByNameLike(entryCategory.getName())
				.ifPresent( (existingCategory) -> validateCategoryName(entryCategory, existingCategory));
	}
	
	private void validateCategoryName(EntryCategory entryCategory, EntryCategory existingCategory) {
		if (entryCategory.isNew()) {
			throw new BusinessException(String.format("Já existe uma categoria com o nome de %s",
					entryCategory.getName()));
		}
		
		if (areNotEqual(entryCategory, existingCategory)) {
			throw new BusinessException(String.format("Já existe uma categoria com o nome de %s",
					entryCategory.getName()));
		}
	}
	
	private boolean areNotEqual(EntryCategory entryCategory, EntryCategory existingCategory) {
		return !existingCategory.getId().equals(entryCategory.getId());
	}
	
	@Transactional
	public void delete(Long categoryId) {
		EntryCategory entryCategory = findByIdOrFail(categoryId);
		
		if (entryCategory.isSystemGenerated()) {
			throw new BusinessException("Não é possível excluir um categoria gerada pelo sistema");
		}
		
		if (entryRepository.existsByCategoryId(categoryId)) {
			throw new BusinessException("Não é possível excluir um categoria que possui lançamentos vinculados");
		}
		
		categoryRepository.delete(entryCategory);
	}
	
	public EntryCategory getDefaultCategoryOrCreate() {
		String categoryName = cashFlowProperties.getDefaultCategoryName();
		return categoryRepository.findOneByNameLike(categoryName)
				.orElseGet(() -> categoryRepository.save(createDefault(categoryName)));
	}
	
	private EntryCategory createDefault(String categoryName) {
		return EntryCategory.builder()
				.name(categoryName)
				.systemGenerated(true)
				.type(EntryType.EXPENSE)
				.build();
	}
	
}
