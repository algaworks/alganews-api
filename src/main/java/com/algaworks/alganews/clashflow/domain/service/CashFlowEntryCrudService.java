package com.algaworks.alganews.clashflow.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import com.algaworks.alganews.clashflow.domain.repository.EntryCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.alganews.clashflow.domain.exception.CashFlowEntryNotFoundException;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.clashflow.domain.repository.CashFlowEntryRepository;
import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CashFlowEntryCrudService {
	
	private final CashFlowEntryRepository entryRepository;
	private final EntryCategoryRepository categoryRepository;
	private final EntryCategoryCrudService categoryCrudService;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
	
	public CashFlowEntry findByIdOrFail(Long entryId) {
		return entryRepository.findById(entryId).orElseThrow(() -> new CashFlowEntryNotFoundException(entryId));
	}
	
	@Transactional
	public CashFlowEntry save(CashFlowEntry entry) {
		entryRepository.detach(entry);
		categoryRepository.detach(entry.getCategory());
		
		if (entry.isNotNew() && entry.isSystemGenerated()) {
			throw new BusinessException("Não é possível alterar um pagamento gerado pelo sistema");
		}
		
		EntryCategory category = categoryCrudService.findByIdOrFail(entry.getCategory().getId());
		entry.setCategory(category);
		
		return entryRepository.save(entry);
	}
	
	@Transactional
	public void deleteAll(Set<Long> entryIds) {
		List<CashFlowEntry> entries = entryRepository.findAllById(entryIds);
		
		entries.forEach(entry -> {
				if (entry.isSystemGenerated()) {
					throw new BusinessException(String.format(
							"Não é possível excluir o pagamento de ID %s, foi gerado pelo sistema", entry.getId()));
				}
			}
		);
		
		entryRepository.deleteAll(entries);
	}
	
	@Transactional
	public void delete(Long entryId) {
		CashFlowEntry entry = findByIdOrFail(entryId);
		
		if (entry.isSystemGenerated()) {
			throw new BusinessException("Não é possível excluir um pagamento gerado pelo sistema");
		}
		
		entryRepository.delete(entry);
	}
	
	@Transactional
	public CashFlowEntry createFrom(Payment payment) {
		EntryCategory category = categoryCrudService.getDefaultCategoryOrCreate();
		
		String entryDescription = generateDescriptionFrom(payment);
		
		CashFlowEntry entry = CashFlowEntry.builder()
				.transactedOn(LocalDate.now())
				.type(EntryType.EXPENSE)
				.description(entryDescription)
				.systemGenerated(true)
				.category(category)
				.amount(payment.getGrandTotalAmount())
				.build();
		
		return entryRepository.save(entry);
	}
	
	private String generateDescriptionFrom(Payment payment) {
		return String.format("Posts de %s de %s a %s",
				payment.getPayee().getName(),
				payment.getAccountingPeriod().getStartsOn().format(formatter),
				payment.getAccountingPeriod().getEndsOn().format(formatter)
		);
	}
	
}
