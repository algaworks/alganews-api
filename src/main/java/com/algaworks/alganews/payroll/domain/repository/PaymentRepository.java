package com.algaworks.alganews.payroll.domain.repository;

import com.algaworks.alganews.common.jpa.CustomJpaRepository;
import com.algaworks.alganews.payroll.domain.model.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CustomJpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
	
	@Override
	@EntityGraph(attributePaths = {"payee"})
	Page<Payment> findAll(@Nullable Specification<Payment> spec, Pageable pageable);
	
	@Override
	@EntityGraph(attributePaths = {"payee", "createdAt", "updatedAt", "bonuses"})
	Optional<Payment> findById(Long id);
}
