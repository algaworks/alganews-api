package com.algaworks.alganews.common.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
	implements CustomJpaRepository<T, ID> {

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
								   EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
	}

}
