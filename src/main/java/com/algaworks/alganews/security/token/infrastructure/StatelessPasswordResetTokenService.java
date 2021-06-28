package com.algaworks.alganews.security.token.infrastructure;

import java.util.Date;

import com.algaworks.alganews.common.util.Base64Encoder;
import com.algaworks.alganews.common.util.HmacSha256;
import com.algaworks.alganews.security.config.SecurityProperties;
import com.algaworks.alganews.security.token.domain.PasswordResetTokenService;
import com.algaworks.alganews.security.token.domain.UnreadableTokenException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatelessPasswordResetTokenService implements PasswordResetTokenService {

	@NonNull
	private final String secret;
	
	private final long timeoutMilliseconds;
	private SecurityProperties.TokenProperties tokenProperties;
	
	public StatelessPasswordResetTokenService(SecurityProperties.TokenProperties tokenProperties) {
		this.tokenProperties = tokenProperties;
		this.secret = tokenProperties.getSecret();
		this.timeoutMilliseconds = tokenProperties.getTimeout().toMillis();
	}

	@Override
	public String makeToken(TokenPayload tokenPayload) {
		return makeTokenWithTimestamp(tokenPayload, new Date().getTime());
	}
	
	public String makeTokenWithTimestamp(TokenPayload tokenPayload, long timestamp) {
		String base64Milliseconds = Base64Encoder.encode(String.valueOf(timestamp));
		String hash = HmacSha256.sign(secret, makeHash(tokenPayload, timestamp));
		String base64UserId = Base64Encoder.encode(tokenPayload.getEmail());
		
		return String.format("%s-%s-%s", base64Milliseconds, base64UserId, hash);
	}
	
	@Override
	public boolean isValid(TokenPayload tokenPayload, String token) {
		if (isExpired(token)) {
			return false;
		}
		
		String newToken = makeTokenWithTimestamp(tokenPayload, extractTimestamp(token));
		return newToken.equals(token);
	}
	
	@Override
	public boolean isExpired(String token) {
		// Token que nunca expira
		if (timeoutMilliseconds < 0) {
			return false;
		}
		
		long timestamp = extractTimestamp(token);
		return new Date().getTime() - timestamp > timeoutMilliseconds;
	}
	

	@Override
	public String getResetUrl() {
		return tokenProperties.getUrl();
	}
	
	@Override
	public String extractUsername(String token) {
		try {
			return Base64Encoder.decode(token.split("-")[1]);
		} catch (Exception e) {
			throw new UnreadableTokenException("Unreadable token payload", e);
		}
	}

	private String makeHash(TokenPayload tokenPayload, long timestamp) {
		return String.format("%s%s%s%s%s", tokenPayload.getUserId(), tokenPayload.getLastLoginAt(), 
				tokenPayload.getEmail(), tokenPayload.getPassword(), timestamp);
	}
	
	private long extractTimestamp(String token) {
		try {
			return Long.parseLong(Base64Encoder.decode(token.split("-")[0]));
		} catch (Exception e) {
			throw new UnreadableTokenException("Unreadable token payload", e);
		}
	}
	
}
