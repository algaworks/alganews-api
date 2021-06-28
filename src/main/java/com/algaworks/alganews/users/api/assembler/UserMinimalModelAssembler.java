package com.algaworks.alganews.users.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.users.api.model.UserMinimalModel;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMinimalModelAssembler {
	
	private final ModelMapper modelMapper;
	
	public UserMinimalModel toModel(User user) {
		return modelMapper.map(user, UserMinimalModel.class);
	}
	
}
