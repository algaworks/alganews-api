package com.algaworks.alganews.common.email.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.alganews.common.email.domain.EmailSenderService;
import com.algaworks.alganews.common.email.infrastructure.FakeEmailSenderService;
import com.algaworks.alganews.common.email.infrastructure.SandboxEmailSenderService;
import com.algaworks.alganews.common.email.infrastructure.SmtpEmailSenderService;

@Configuration
@AllArgsConstructor
public class EmailConfig {

	private final EmailProperties emailProperties;

	@Bean
	public EmailSenderService envioEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEmailSenderService();
			case SMTP:
				return new SmtpEmailSenderService();
			case SANDBOX:
				return new SandboxEmailSenderService();
			default:
				return null;
		}
	}

}