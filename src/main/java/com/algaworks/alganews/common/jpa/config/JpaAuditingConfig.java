package com.algaworks.alganews.common.jpa.config;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "auditingDateTimeProvider")
@AllArgsConstructor
public class JpaAuditingConfig {
	
	private final AlgaSecurity algaSecurity;
	
	@Bean
	public DateTimeProvider auditingDateTimeProvider() {
		return () -> Optional.of(OffsetDateTime.now());
	}
	
	@Bean
	public AuditorAware<User> auditorAware() {
		return algaSecurity::getAuthenticatedUser;
	}
	
}