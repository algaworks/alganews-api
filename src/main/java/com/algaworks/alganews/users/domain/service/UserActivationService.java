package com.algaworks.alganews.users.domain.service;

import javax.transaction.Transactional;

import com.algaworks.alganews.users.domain.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserActivationService {
	
	private final UserCrudService userCrudService;
	private final AlgaSecurity algaSecurity;
	
	@Transactional
	public void activate(Long userId) {
		User user = userCrudService.findByIdOrFail(userId);
		verifyIfUserCanActivateOrDeactivate(user);
		if (user.isActive())
			throw new BusinessException("Usuário já está ativo.");
		user.activate();
	}
	
	@Transactional
	public void deactivate(Long userId) {
		User user = userCrudService.findByIdOrFail(userId);
		verifyIfUserCanActivateOrDeactivate(user);
		if (user.isNotActive())
			throw new BusinessException("Usuário já está inativo.");
		user.deactivate();
	}
	
	private void verifyIfUserCanActivateOrDeactivate(User user) {
		User authenticatedUser = algaSecurity.getAuthenticatedUserOrFail();
		if (isSameUser(user, authenticatedUser))
			throw new BusinessException("Um usuário não pode ativar ou desativar a si mesmo.");
		if (authenticatedUser.cannotActivateUserOfRole(user.getRole()))
			throw new AccessDeniedException(
					String.format("Este usuário não tem permissão para ativar ou desativar um usuário de perfil %s.",
							user.getRole().getLocalizedName()));
	}
	
	private boolean isSameUser(User user, User authenticatedUser) {
		return user.getId().equals(authenticatedUser.getId());
	}
	
}
