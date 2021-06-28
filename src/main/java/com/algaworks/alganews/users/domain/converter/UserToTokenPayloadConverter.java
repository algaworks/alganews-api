package com.algaworks.alganews.users.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.security.token.domain.PasswordResetTokenService;
import com.algaworks.alganews.security.token.domain.PasswordResetTokenService.TokenPayload;
import com.algaworks.alganews.users.domain.model.User;

@Component
public class UserToTokenPayloadConverter implements Converter<User, PasswordResetTokenService.TokenPayload> {

	@Override
	public TokenPayload convert(User user) {
		return PasswordResetTokenService.TokenPayload.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.password(user.getPassword())
			.lastLoginAt(user.getLastLogin())
			.build();
	}

}
