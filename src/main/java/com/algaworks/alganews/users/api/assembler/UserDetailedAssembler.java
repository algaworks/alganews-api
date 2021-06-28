package com.algaworks.alganews.users.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.view.UserMetricsProjection;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDetailedAssembler {
	
	private final ModelMapper modelMapper;
	private final UserMetricsModelAssembler userMetricsModelAssembler;
	private final AlgaSecurity algaSecurity;
	
	public UserDetailedModel toModel(User user, UserMetricsProjection metrics) {
		UserDetailedModel model = modelMapper.map(user, UserDetailedModel.class);
		model.setMetrics(userMetricsModelAssembler.toModel(metrics));
		model.setCanBeActivated(algaSecurity.canActivateUserOfRole(user.getRole()) && !model.isActive());
		model.setCanBeDeactivated(algaSecurity.canActivateUserOfRole(user.getRole()) && model.isActive());
		model.setCanSensitiveDataBeUpdated(algaSecurity.canSensitiveDataBeUpdated(user));
		return model;
	}
	
}
