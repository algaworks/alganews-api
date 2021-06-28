package com.algaworks.alganews.users.domain.exception;

import com.algaworks.alganews.common.domain.BusinessException;

public class ExpiredTokenException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public ExpiredTokenException() {
		super("O token de redefinição de senha expirou");
	}
	
}
