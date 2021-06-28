package com.algaworks.alganews.users.domain.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.alganews.users.domain.event.UserCreatedEvent;
import com.algaworks.alganews.users.domain.service.UserOnboardingService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Slf4j
public class UserOnboardingListener {
	
	private final UserOnboardingService userOnboardingService;
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Async
	public void onUserCreatedEvent(UserCreatedEvent event) {
		try {
			userOnboardingService.startOnboarding(event.getUser());
		} catch (Exception e) {
			log.error(String.format("Erro ao realizar o onboarding do usuário %s.", event.getUser().getName()), e);
		}
	}
	
}
