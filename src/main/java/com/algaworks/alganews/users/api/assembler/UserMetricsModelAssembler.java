package com.algaworks.alganews.users.api.assembler;

import com.algaworks.alganews.users.api.model.UserMetricsModel;
import com.algaworks.alganews.users.domain.view.UserMetricsProjection;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMetricsModelAssembler {
	
	private final ModelMapper modelMapper;
	
	public UserMetricsModel toModel(UserMetricsProjection userMetricsProjection) {
		return modelMapper.map(userMetricsProjection, UserMetricsModel.class);
	}
	
}
