package com.algaworks.alganews.users.domain.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.common.email.domain.EmailSenderService;
import com.algaworks.alganews.security.token.domain.PasswordResetTokenService;
import com.algaworks.alganews.security.token.domain.PasswordResetTokenService.TokenPayload;
import com.algaworks.alganews.users.domain.model.User;

@Service
public class UserOnboardingService {

	private final PasswordResetTokenService passwordResetTokenService;
	private final EmailSenderService emailSenderService;
	private final ConversionService conversionService;
	
	public UserOnboardingService(PasswordResetTokenService passwordResetTokenService,
								  EmailSenderService emailSenderService, ConversionService conversionService) {
		this.passwordResetTokenService = passwordResetTokenService;
		this.emailSenderService = emailSenderService;
		this.conversionService = conversionService;
	}
	
	public void startOnboarding(User user) {
		if (user.hasAlreadyOnboarded()) {
			String message = "A integração do usuário %s não pode ser realizada porque já foi feita anteriormente";
			throw new BusinessException(String.format(message, user.getEmail()));
		}
		
		var tokenPayload = conversionService.convert(user, TokenPayload.class);
		
		String token = passwordResetTokenService.makeToken(tokenPayload);
		sendEmail(user, token);
	}
	
	private void sendEmail(User user, String token) {
		var message = EmailSenderService.Message.builder()
				.subject("AlgaNews - Cadastre a sua senha de acesso")
				.body("users/user-onboarding.html")
				.data("userName", user.getName())
				.data("resetUrl", makeUrl(token))
				.to(user.getEmail())
				.build();
		
		emailSenderService.send(message);
	}
	
	private String makeUrl(String token) {
		return passwordResetTokenService.getResetUrl()
				.replace("{token}", token);
	}
	
}
