package com.algaworks.alganews.users.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class UserInputDisassembler {

	private final ModelMapper modelMapper;
	
	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}
	
	public void copyToDomainObject(UserInput userInput, User user) {
		modelMapper.map(userInput, user);

		//Aplicando lista devido ao model mapper agir como um "Patch" ao invés de "PUT"

		if (userInput.getSkills() == null || userInput.getSkills().isEmpty()) {
			user.setSkills(new ArrayList<>());
		}
	}
	
}
