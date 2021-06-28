package com.algaworks.alganews.security.config;

import java.time.Duration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("alganews.security")
public class SecurityProperties {
	
	private boolean disabled = false;
	private Long defaultUserIdIfDisabled = 1L;
	private String logoutDefaultRedirectUrl = "/";
	
	@Valid
	private ResetTokenProperties resetToken = new ResetTokenProperties();
	
	@Valid
	private ResourceServerProperties resourceServer = new ResourceServerProperties();
	
	@Getter
	@Setter
	public class ResourceServerProperties {
		
		@NotEmpty
		@Size(min = 32)
		private String tokensSigningKey;
		
	}
	
	@Getter
	@Setter
	public class ResetTokenProperties {
		
		@Valid
		private PasswordResetProperties passwordReset = new PasswordResetProperties();
		
	}
	
	@Getter
	@Setter
	public class PasswordResetProperties implements TokenProperties {
		
		@URL
		@NotEmpty
		private String url;
		
		@NotEmpty
		private String secret;
		
		@NotNull
		private Duration timeout;
		
	}
	
	public interface TokenProperties {
		
		String getUrl();
		String getSecret();
		Duration getTimeout();
		
	}
	
}
