package com.algaworks.alganews.clashflow.domain.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.alganews.clashflow.domain.filter.EntryFilter;
import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;

public class EntrySpecs {
	
	private EntrySpecs() {
	
	}
	
	public static Specification<CashFlowEntry> usingFilter(EntryFilter filter) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (filter.getType() != null) {
				predicates.add(builder.equal(root.get("type"), filter.getType()));
			}

			if (filter.getYearMonth() != null) {
				predicates.add(builder.between(root.get("transactedOn"),
						filter.getYearMonth().atDay(1),
						filter.getYearMonth().atEndOfMonth()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
