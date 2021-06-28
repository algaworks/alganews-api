package com.algaworks.alganews.users.domain.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.alganews.users.domain.view.UserMetricsProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.alganews.common.jpa.CustomJpaRepository;
import com.algaworks.alganews.users.domain.model.Role;
import com.algaworks.alganews.users.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Override
	@EntityGraph(attributePaths = {"skills", "createdBy", "updatedBy"})
	Optional<User> findById(Long id);
	
	@EntityGraph(attributePaths = {"skills", "createdBy", "updatedBy"})
	Optional<User> findByIdAndRole(Long id, Role role);
	
	Optional<Role> getRoleByUserId(@Param("userId") Long userId);
	
	UserMetricsProjection getMetricsByUserId(@Param("id") Long id);
	
	@EntityGraph(attributePaths = {"skills", "createdBy", "updatedBy"})
	List<User> findAllByRole(Role role, Sort sort);
	
	boolean existsByEmailAndUserIdDiffersFrom(String email, Long userId);
	
	boolean existsByEmail(String email);
	
}
