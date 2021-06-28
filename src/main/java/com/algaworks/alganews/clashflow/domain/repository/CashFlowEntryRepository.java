package com.algaworks.alganews.clashflow.domain.repository;

import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;
import com.algaworks.alganews.common.jpa.CustomJpaRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashFlowEntryRepository extends CustomJpaRepository<CashFlowEntry, Long>,
		JpaSpecificationExecutor<CashFlowEntry> {
	
	boolean existsByCategoryId(Long id);
	
	@Override
	@EntityGraph(attributePaths = {"category"})
	List<CashFlowEntry> findAll(@Nullable Specification<CashFlowEntry> spec);
	
	@Override
	@EntityGraph(attributePaths = {"category", "createdBy", "updatedBy"})
	Optional<CashFlowEntry> findById(Long id);
	
}
