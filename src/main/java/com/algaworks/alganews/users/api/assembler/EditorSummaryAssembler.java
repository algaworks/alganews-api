package com.algaworks.alganews.users.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.users.api.model.EditorSummaryModel;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EditorSummaryAssembler {
	
	private final ModelMapper modelMapper;
	
	public EditorSummaryModel toModel(User editor) {
		return modelMapper.map(editor, EditorSummaryModel.class);
	}
	
	public List<EditorSummaryModel> toCollectionModel(List<User> editors) {
		return editors.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
