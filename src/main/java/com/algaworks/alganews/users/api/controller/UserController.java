package com.algaworks.alganews.users.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.security.CheckSecurity;
import com.algaworks.alganews.users.api.assembler.EditorDetailedAssembler;
import com.algaworks.alganews.users.api.assembler.EditorSummaryAssembler;
import com.algaworks.alganews.users.api.assembler.UserDetailedAssembler;
import com.algaworks.alganews.users.api.assembler.UserInputDisassembler;
import com.algaworks.alganews.users.api.assembler.UserSummaryAssembler;
import com.algaworks.alganews.users.api.model.EditorDetailedModel;
import com.algaworks.alganews.users.api.model.EditorSummaryModel;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.api.model.UserSummaryModel;
import com.algaworks.alganews.users.domain.filter.UserFilter;
import com.algaworks.alganews.users.domain.model.Role;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.view.UserMetricsProjection;
import com.algaworks.alganews.users.domain.repository.UserRepository;
import com.algaworks.alganews.users.domain.repository.UserSpecs;
import com.algaworks.alganews.users.domain.service.UserActivationService;
import com.algaworks.alganews.users.domain.service.UserCrudService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	private final UserCrudService userCrudService;
	private final UserActivationService userActivationService;
	
	private final UserSummaryAssembler userSummaryAssembler;
	private final UserDetailedAssembler userDetailedAssembler;
	
	private final EditorSummaryAssembler editorSummaryAssembler;
	private final EditorDetailedAssembler editorDetailedAssembler;
	
	private final UserInputDisassembler userInputDisassembler;
	
	@CheckSecurity.Users.CanList
	@GetMapping
	public List<UserSummaryModel> search(UserFilter userFilter,
				@SortDefault(value = "name", direction = Sort.Direction.ASC) Sort sort) {
		List<User> users = userRepository.findAll(UserSpecs.usingFilter(userFilter), sort);
		return userSummaryAssembler.toCollectionModel(users);
	}
	
	@CheckSecurity.Users.CanCreateOrEdit
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDetailedModel create(@Valid @RequestBody UserInput userInput) {
		User user = userInputDisassembler.toDomainObject(userInput);
		
		user = userCrudService.save(user);
		UserMetricsProjection metrics = userRepository.getMetricsByUserId(user.getId());
		
		return userDetailedAssembler.toModel(user, metrics);
	}
	
	@CheckSecurity.Users.CanCreateOrEdit
	@PutMapping("/{userId}")
	public UserDetailedModel update(@PathVariable Long userId,
									@Valid @RequestBody UserInput userInput) {
		User user = userCrudService.findByIdOrFail(userId);
		
		userInputDisassembler.copyToDomainObject(userInput, user);
		user = userCrudService.save(user);
		
		UserMetricsProjection metrics = userRepository.getMetricsByUserId(userId);
		
		return userDetailedAssembler.toModel(user, metrics);
	}
	
	@CheckSecurity.Users.CanGetOne
	@GetMapping("/{userId}")
	public UserDetailedModel findById(@PathVariable Long userId) {
		User user = userCrudService.findByIdOrFail(userId);
		UserMetricsProjection metrics = userRepository.getMetricsByUserId(userId);
		
		return userDetailedAssembler.toModel(user, metrics);
	}
	
	@CheckSecurity.Users.CanCreateOrEdit
	@PutMapping("/{userId}/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long userId) {
		userActivationService.activate(userId);
	}
	
	@CheckSecurity.Users.CanCreateOrEdit
	@DeleteMapping("/{userId}/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivate(@PathVariable Long userId) {
		userActivationService.deactivate(userId);
	}
	
	@CheckSecurity.Authenticated
	@GetMapping("/editors")
	public List<EditorSummaryModel> findAllEditors(
			@SortDefault(value = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<User> editors = userRepository.findAllByRole(Role.EDITOR, sort);
		return editorSummaryAssembler.toCollectionModel(editors);
	}
	
	@CheckSecurity.Authenticated
	@GetMapping("/editors/{editorId}")
	public EditorDetailedModel findEditorById(@PathVariable Long editorId) {
		User editor = userCrudService.findEditorByIdOrFail(editorId);
		return editorDetailedAssembler.toModel(editor);
	}

}
