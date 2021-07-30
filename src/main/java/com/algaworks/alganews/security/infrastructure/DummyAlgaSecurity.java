package com.algaworks.alganews.security.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.security.config.SecurityProperties;
import com.algaworks.alganews.users.domain.repository.UserRepository;

@ConditionalOnProperty(name = "alganews.security.disabled", havingValue = "true")
@Component("algaSecurity")
public class DummyAlgaSecurity extends AlgaSecurity {
	
	private final SecurityProperties securityProperties;
	
	public DummyAlgaSecurity(UserRepository userRepository, PostRepository postRepository, 
			SecurityProperties securityProperties) {
		super(userRepository, postRepository);
		this.securityProperties = securityProperties;
	}

	@Override
	public boolean hasAuthority(String authorityName) {
		return true;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}
	
	@Override
	public Long getUserId() {
		return securityProperties.getDefaultUserIdIfDisabled();
	}
	
}
