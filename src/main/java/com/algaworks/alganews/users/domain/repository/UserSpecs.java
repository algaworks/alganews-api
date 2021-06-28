package com.algaworks.alganews.users.domain.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.algaworks.alganews.users.domain.filter.UserFilter;
import com.algaworks.alganews.users.domain.model.User;

public class UserSpecs {
	
	private UserSpecs() {
	
	}

	public static Specification<User> usingFilter(UserFilter filter) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (StringUtils.isNotEmpty(filter.getEmail())) {
				String searchTerm = "%" + filter.getEmail().trim() + "%";
				predicates.add(builder.like(root.get("email"), searchTerm));
			}
			
			if (StringUtils.isNotEmpty(filter.getName())) {
				String searchTerm = "%" + filter.getName().trim() + "%";
				predicates.add(builder.like(root.get("name"), searchTerm));
			}
			
			if (StringUtils.isNotEmpty(filter.getTerm())) {
				String searchTerm = "%" + filter.getTerm().trim() + "%";
				predicates.add(builder.or(
						builder.like(root.get("name"), searchTerm),
						builder.like(root.get("email"), searchTerm),
						builder.like(root.get("taxpayerId"), searchTerm)
				));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
