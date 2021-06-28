package com.algaworks.alganews.security.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.repository.UserRepository;

@ConditionalOnProperty(name = "alganews.security.disabled", havingValue = "false", matchIfMissing = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component("algaSecurity")
public class JwtAlgaSecurity extends AlgaSecurity {
	
	public JwtAlgaSecurity(UserRepository userRepository, PostRepository postRepository) {
		super(userRepository, postRepository);
	}

	@Override
	public Long getUserId() {
		var jwt = getJwtToken();
		if (jwt == null) return null;
		return jwt.getClaim("alganews:user_id");
	}
	
	private Jwt getJwtToken() {
		if (getAuthentication() != null && getAuthentication().getPrincipal() instanceof Jwt) {
			return (Jwt) getAuthentication().getPrincipal();
		}
		
		return null;
	}
	
}
