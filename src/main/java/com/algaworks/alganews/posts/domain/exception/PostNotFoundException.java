package com.algaworks.alganews.posts.domain.exception;

import com.algaworks.alganews.common.domain.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(Long id) {
		super(String.format("Não existe um post com o código %s", id));
	}
	
}
