package com.algaworks.alganews.common.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	void detach(T entity);
	
}
