package com.algaworks.alganews.clashflow.domain.exception;

import com.algaworks.alganews.common.domain.EntityNotFoundException;

public class EntryCategoryNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	
	public EntryCategoryNotFoundException() {
		super("Categoria não encontrada");
	}
	
	public EntryCategoryNotFoundException(Long id) {
		super(String.format("Não existe uma categoria com o código %s", id));
	}
	
}
