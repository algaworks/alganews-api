package com.algaworks.alganews.common.email.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("alganews.email")
public class EmailProperties {
	
	private Impl impl = Impl.FAKE;
	
	@NotNull
	private String from;
	
	private Sandbox sandbox = new Sandbox();
	
	public enum Impl {
		SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class Sandbox {
		
		private String to;
		
	}
	
}
