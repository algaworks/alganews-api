package com.algaworks.alganews.security.infrastructure;

import com.algaworks.alganews.users.domain.model.Role;
import com.algaworks.alganews.users.domain.model.User;
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
	public boolean hasAdminisrativeRoles() {
		User user = super.getAuthenticatedUserOrFail();
		return user.getRole().equals(Role.MANAGER) || user.getRole().equals(Role.ASSISTANT);
	}

	@Override
	public boolean hasEditorRole() {
		User user = super.getAuthenticatedUserOrFail();
		return user.getRole().equals(Role.EDITOR);
	}

	@Override
	public boolean hasAllWriteScope() {
		return true;
	}

	@Override
	public boolean hasAllReadScope() {
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
