package com.algaworks.alganews.clashflow.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.common.jpa.CustomJpaRepository;

@Repository
public interface EntryCategoryRepository extends CustomJpaRepository<EntryCategory, Long>,
		JpaSpecificationExecutor<EntryCategory>, EntryCategoryQuery {
	
	@Override
	@EntityGraph(attributePaths = {"createdBy", "updatedBy"})
	Optional<EntryCategory> findById(Long id);
	
	Optional<EntryCategory> findOneByNameLike(String name);
	
	
}
