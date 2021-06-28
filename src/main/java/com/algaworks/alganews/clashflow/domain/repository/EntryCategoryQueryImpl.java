package com.algaworks.alganews.clashflow.domain.repository;

import com.algaworks.alganews.clashflow.domain.model.CashFlowEntry;
import com.algaworks.alganews.clashflow.domain.model.EntryCategory;
import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategoryDetailedProjection;
import com.algaworks.alganews.clashflow.domain.model.projection.EntryCategorySummaryProjection;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EntryCategoryQueryImpl implements EntryCategoryQuery {
	
	private final EntityManager entityManager;
	
	@Override
	public List<EntryCategorySummaryProjection> findAllAsProjectionByType(EntryType type, Sort sort) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EntryCategorySummaryProjection> query = builder.createQuery(EntryCategorySummaryProjection.class);
		Root<EntryCategory> root = query.from(EntryCategory.class);
		
		Subquery subquery = query.subquery(Long.class);
		Root subRoot = subquery.from(CashFlowEntry.class);
		Join<CashFlowEntry, EntryCategory> cashFlowJoin = subRoot.join("category");
		subquery.select(builder.count(subRoot.get("id")));
		subquery.where(builder.equal(root.get("id"), cashFlowJoin.get("id")));
		
		Selection<EntryCategorySummaryProjection> selection = builder.construct(
				EntryCategorySummaryProjection.class,
				root.get("id"),
				root.get("name"),
				root.get("type"),
				root.get("systemGenerated"),
				subquery
		);
		
		query.select(selection);
		
		if (type != null) {
			query.where(builder.equal(root.get("type"), type));
		}
		
		if (sort != null) {
			List<Order> sortOrders = createSortOrders(sort, builder, root);
			query.orderBy(sortOrders);
		}
		
		TypedQuery<EntryCategorySummaryProjection> typedQuery = entityManager.createQuery(query);
		
		return typedQuery.getResultList();
	}
	
	private List<Order> createSortOrders(Sort sort, CriteriaBuilder builder, Root<EntryCategory> root) {
		List<Order> orderList = new ArrayList();
		
		for (Sort.Order order : sort) {
			if (order.isDescending()) {
				orderList.add(builder.desc(root.get(order.getProperty())));
			} else {
				orderList.add(builder.asc(root.get(order.getProperty())));
			}
		}
		
		return orderList;
	}
	
	@Override
	public Optional<EntryCategoryDetailedProjection> findOneByIdAsProjection(Long id) {
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<EntryCategoryDetailedProjection> query = criteria.createQuery(EntryCategoryDetailedProjection.class);
		Root<EntryCategory> categoryRoot = query.from(EntryCategory.class);
		
		Subquery subquery = query.subquery(Long.class);
		Root subRoot = subquery.from(CashFlowEntry.class);
		Join<CashFlowEntry, EntryCategory> cashFlowJoin = subRoot.join("category");
		subquery.select(criteria.count(subRoot.get("id")));
		subquery.where(criteria.equal(categoryRoot.get("id"), cashFlowJoin.get("id")));
		
		Selection<EntryCategoryDetailedProjection> selection = criteria.construct(
				EntryCategoryDetailedProjection.class,
				categoryRoot.get("id"),
				categoryRoot.get("name"),
				categoryRoot.get("type"),
				categoryRoot.get("systemGenerated"),
				subquery,
				categoryRoot.get("createdAt"),
				categoryRoot.get("updatedAt"),
				categoryRoot.get("createdBy"),
				categoryRoot.get("updatedBy")
		);
		
		query.select(selection);
		
		query.where(criteria.equal(categoryRoot.get("id"), id));
		
		TypedQuery<EntryCategoryDetailedProjection> typedQuery = entityManager.createQuery(query);
		
		try {
			return Optional.ofNullable(typedQuery.getSingleResult());
		} catch (EmptyResultDataAccessException exception) {
			return Optional.empty();
		}
		
	}
}
