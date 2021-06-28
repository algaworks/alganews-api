package com.algaworks.alganews.security.token.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.alganews.security.config.SecurityProperties;
import com.algaworks.alganews.security.token.domain.PasswordResetTokenService;
import com.algaworks.alganews.security.token.infrastructure.StatelessPasswordResetTokenService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class PasswordResetTokenConfig {
	
	private final SecurityProperties securityProperties;
	
	@Bean
	public PasswordResetTokenService passwordUpdate() {
		return new StatelessPasswordResetTokenService(securityProperties.getResetToken().getPasswordReset());
	}
	
}
