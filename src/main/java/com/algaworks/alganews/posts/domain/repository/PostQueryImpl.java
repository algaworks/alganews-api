package com.algaworks.alganews.posts.domain.repository;

import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.posts.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
@AllArgsConstructor
public class PostQueryImpl implements PostQuery {
	
	private final EntityManager entityManager;
	
	@Override
	public List<Post> findAllByPaymentId(Long paymentId, Sort sort) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(Post.class);
		Root root = query.from(Post.class);
		
		root.fetch("editor");
		root.fetch("updatedBy");

		Subquery sub = query.subquery(Long.class);
		Root subPaymentRoot = sub.from(Payment.class);
		Join<Payment, Post> subPostJoin = subPaymentRoot.join("posts");
		sub.select(subPostJoin.get("id"));
		sub.where(builder.equal(root.get("id"), subPostJoin.get("id")));
		sub.where(builder.equal(subPaymentRoot.get("id"), paymentId));

		query.where(root.get("id").in(sub));
		
		if (sort != null) {
			Iterator<Sort.Order> orderIterator = sort.iterator();
			
			List<Order> orderList = new ArrayList();
			
			while (orderIterator.hasNext()) {
				Sort.Order order = orderIterator.next();
				if (order.isDescending()) {
					orderList.add(builder.desc(root.get(order.getProperty())));
				} else {
					orderList.add(builder.asc(root.get(order.getProperty())));
				}
			}
			query.orderBy(orderList);
		}
		
		return entityManager.createQuery(query).getResultList();
	}
}
