package com.algaworks.alganews.security.token;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.algaworks.alganews.security.token.domain.PasswordResetTokenService.TokenPayload;
import com.algaworks.alganews.security.token.infrastructure.StatelessPasswordResetTokenService;
import com.algaworks.alganews.users.domain.model.User;

public class TokenManagerTest {
	
	private final String secret = "#algaSecret";
	
	StatelessPasswordResetTokenService fiveMinutesTokenManager = new StatelessPasswordResetTokenService(secret, 5 * 60000);
	StatelessPasswordResetTokenService unspirableTokenManager = new StatelessPasswordResetTokenService(secret, -1);
	
	@Test
	public void shouldBeValidToken_WhenTheDateIsInTheLimit() {
	
		User user = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload tokenPayload = TokenPayload.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.lastLoginAt(user.getLastLogin())
				.build();
		
		String token = fiveMinutesTokenManager.makeToken(tokenPayload);
		
		Assertions.assertTrue(
				fiveMinutesTokenManager.isValid(tokenPayload, token)
		);
	}
	
	@Test
	public void shouldBeInvalidToken_WhenTheDateIsNotInTheLimit() {
		
		User user = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload tokenPayload = TokenPayload.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.lastLoginAt(user.getLastLogin())
				.build();
		
		LocalDateTime dateTime = LocalDateTime.now().minus(Duration.of(10, ChronoUnit.MINUTES));
		Date pastDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		String token = fiveMinutesTokenManager.makeTokenWithTimestamp(tokenPayload, pastDate.getTime());
		
		Assertions.assertFalse(
				fiveMinutesTokenManager.isValid(tokenPayload, token)
		);
	}
	
	@Test
	public void shouldBeInvalidToken_WhenTheUserChangeAnyInfo() {
		
		User user = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload tokenPayload = TokenPayload.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.lastLoginAt(user.getLastLogin())
				.build();
		
		User userChanged = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#9878945454"))
				.build();
		
		TokenPayload tokenPayloadChanged = TokenPayload.builder()
				.userId(userChanged.getId())
				.email(userChanged.getEmail())
				.password(userChanged.getPassword())
				.lastLoginAt(userChanged.getLastLogin())
				.build();
		
		LocalDateTime dateTime = LocalDateTime.now().minus(Duration.of(10, ChronoUnit.MINUTES));
		Date pastDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		String token = fiveMinutesTokenManager.makeTokenWithTimestamp(tokenPayload, pastDate.getTime());
		
		Assertions.assertFalse(
				fiveMinutesTokenManager.isValid(tokenPayloadChanged, token)
		);
	}
	
	@Test
	public void shouldBeValidToken_WhenTheTokenHasNoDateLimit() {
		
		User user = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload tokenPayload = TokenPayload.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.lastLoginAt(user.getLastLogin())
				.build();
		
		String token = unspirableTokenManager.makeToken(tokenPayload);
		
		Assertions.assertTrue(
				unspirableTokenManager.isValid(tokenPayload, token)
		);
	}
	
	@Test
	public void shouldBeInvalidToken_WhenTheTokenHasNoDateLimitAndTheUserLoggedIn() {
		
		User originalUser = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.lastLogin(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload originalTokenPayload = TokenPayload.builder()
				.userId(originalUser.getId())
				.email(originalUser.getEmail())
				.password(originalUser.getPassword())
				.lastLoginAt(originalUser.getLastLogin())
				.build();
		
		String token = unspirableTokenManager.makeToken(originalTokenPayload);
		
		User userAfterLogin = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(originalUser.getCreatedAt())
				.updatedAt(originalUser.getCreatedAt())
				.lastLogin(OffsetDateTime.now().plusDays(1))
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.build();
		
		TokenPayload afterLoginTokenPayload = TokenPayload.builder()
				.userId(userAfterLogin.getId())
				.email(userAfterLogin.getEmail())
				.password(userAfterLogin.getPassword())
				.lastLoginAt(userAfterLogin.getLastLogin())
				.build();
		
		Assertions.assertFalse(
				unspirableTokenManager.isValid(afterLoginTokenPayload, token)
		);
	}
	
}
