package com.algaworks.alganews.users.domain.service;

import javax.transaction.Transactional;

import com.algaworks.alganews.common.storage.domain.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.event.UserCreatedEvent;
import com.algaworks.alganews.users.domain.exception.EditorNotFoundException;
import com.algaworks.alganews.users.domain.exception.UserNotFoundException;
import com.algaworks.alganews.users.domain.model.Role;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class UserCrudService {
	
	private final UserRepository userRepository;
	private final UserAvatarService userAvatarService;
	private final ApplicationEventPublisher eventPublisher;
	private final AlgaSecurity algaSecurity;
	
	public User findByIdOrFail(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User findEditorByIdOrFail(Long id) {
		return userRepository.findByIdAndRole(id, Role.EDITOR)
				.orElseThrow(() -> new EditorNotFoundException(id));
	}
	
	@Transactional
	public User save(User user) {
		if (user.isNew()) {
			return create(user);
		}
		return edit(user);
	}
	
	@Transactional
	private User create(User user) {
		userRepository.detach(user);
		validateUserEmailAvailability(user);
		validateRoleGrantPolicies(user);
		
		applyUserOnSkills(user);
		user.activate();
		user = userRepository.save(user);
		userRepository.flush();
		
		eventPublisher.publishEvent(new UserCreatedEvent(user));
		updateImage(user);
		
		return user;
	}
	
	@Transactional
	private User edit(User user) {
		userRepository.detach(user);
		
		User existingUser = findByIdOrFail(user.getId());
		userRepository.detach(existingUser);
		
		validateSensitiveFieldsUpdate(user, existingUser);
		validateUserEmailAvailability(user);
		validateRoleGrantPolicies(user);
		
		applyUserOnSkills(user);
		
		user = userRepository.save(user);
		userRepository.flush();
		
		updateImage(user, existingUser);
		
		return user;
	}
	
	private void updateImage(User user) {
		try {
			userAvatarService.updateImage(user);
		} catch (StorageFileNotFoundException e) {
			log.error(e.getMessage());
			throw new BusinessException("Um ou mais arquivos não foram encontrados.", e);
		}
	}
	
	private void updateImage(User user, User existingUser) {
		try {
			userAvatarService.updateImage(user, existingUser);
		} catch (StorageFileNotFoundException e) {
			log.error(e.getMessage());
			throw new BusinessException("Um ou mais arquivos não foram encontrados.", e);
		}
	}
	
	private void validateSensitiveFieldsUpdate(User userToUpdate, User existingUser) {
		User authenticatedUser = algaSecurity.getAuthenticatedUserOrFail();
		
		if (userToUpdate.isSensitiveFieldsUpdateViolated(authenticatedUser, existingUser)) {
			throw new AccessDeniedException(
					"Você não tem poderes para alterar dados sensíveis de um gerente (e-mail, telefone ou perfil)");
		}
	}

	private void validateRoleGrantPolicies(User userToBeGranted) {
		User grantorUser = algaSecurity.getAuthenticatedUserOrFail();
		Role newRole = userToBeGranted.getRole();
		Role oldRoleToBeReplaced = null;
		
		if (userToBeGranted.isNotNew()) {
			oldRoleToBeReplaced = userRepository.getRoleByUserId(userToBeGranted.getId()).orElse(null);
		}
		
		if (grantorUser.cannotGrantRole(newRole, oldRoleToBeReplaced)) {
			throw new BusinessException("Você não pode atribuir um perfil com poderes acima do seu");
		}
	}
	
	private void validateUserEmailAvailability(User user) {
		if (user.isNotNew() && isEmailInUse(user)
				|| user.isNew() && userRepository.existsByEmail(user.getEmail())) {
			throw new BusinessException(String.format("Já existe um usuário com o e-mail %s", user.getEmail()));
		}
	}
	
	private boolean isEmailInUse(User user) {
		return userRepository.existsByEmailAndUserIdDiffersFrom(user.getEmail(), user.getId());
	}
	
	private void applyUserOnSkills(User user) {
		if (user.getSkills() == null || user.getSkills().isEmpty()) {
			user.setSkills(new ArrayList<>());
		}
		user.getSkills().forEach(skill -> skill.setUser(user));
	}
	
}
