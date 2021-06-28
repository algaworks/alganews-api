package com.algaworks.alganews.payroll.domain.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.algaworks.alganews.payroll.domain.filter.PaymentFilter;
import com.algaworks.alganews.payroll.domain.model.Payment;

public class PaymentSpecs {
	
	private PaymentSpecs() {
	
	}
	
	public static Specification<Payment> usingFilter(PaymentFilter filter) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (StringUtils.isNotEmpty(filter.getPayeeEmail())) {
				String searchTerm = "%" + filter.getPayeeEmail().trim() + "%";
				predicates.add(builder.like(root.get("payee").get("email"), searchTerm));
			}
			
			if (filter.getPayeeId() != null) {
				predicates.add(builder.equal(root.get("payee"), filter.getPayeeId()));
			}
			
			if (filter.getScheduledToYearMonth() != null) {
				YearMonth filterYearMonth = filter.getScheduledToYearMonth();
				LocalDate startOn = filterYearMonth.atDay(1);
				LocalDate endsOn = filterYearMonth.atDay(filterYearMonth.lengthOfMonth());
				predicates.add(builder.between(root.get("scheduledTo"), startOn, endsOn));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
