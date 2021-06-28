package com.algaworks.alganews.users.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.api.model.UserSummaryModel;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserSummaryAssembler {
	
	private final ModelMapper modelMapper;
	private final AlgaSecurity algaSecurity;
	
	public UserSummaryModel toModel(User user) {
		UserSummaryModel model = modelMapper.map(user, UserSummaryModel.class);
		model.setCanBeActivated(algaSecurity.canActivateUser(user));
		model.setCanBeDeactivated(algaSecurity.canDeactivateUser(user));
		model.setCanSensitiveDataBeUpdated(algaSecurity.canSensitiveDataBeUpdated(user));
		return model;
	}
	
	public List<UserSummaryModel> toCollectionModel(List<User> users) {
		return users.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
