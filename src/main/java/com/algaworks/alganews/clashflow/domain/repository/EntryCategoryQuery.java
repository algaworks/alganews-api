package com.algaworks.alganews.clashflow.domain.repository;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategoryDetailedProjection;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategorySummaryProjection;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EntryCategoryQuery {
	
	List<EntryCategorySummaryProjection> findAllAsProjectionByType(EntryType type, Sort sort);
	
	Optional<EntryCategoryDetailedProjection> findOneByIdAsProjection(Long id);
	
}
