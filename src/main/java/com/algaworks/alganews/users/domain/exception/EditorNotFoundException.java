package com.algaworks.alganews.users.domain.exception;

import com.algaworks.alganews.common.domain.EntityNotFoundException;

public class EditorNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public EditorNotFoundException(Long id) {
		super(String.format("Não existe editor com o id %s", id));
	}
	
}
