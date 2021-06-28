package com.algaworks.alganews.users.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.users.api.model.EditorDetailedModel;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EditorDetailedAssembler {
	
	private final ModelMapper modelMapper;
	
	public EditorDetailedModel toModel(User editor) {
		return modelMapper.map(editor, EditorDetailedModel.class);
	}
	
}
