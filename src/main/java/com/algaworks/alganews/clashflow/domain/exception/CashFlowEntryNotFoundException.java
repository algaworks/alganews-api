package com.algaworks.alganews.clashflow.domain.exception;

import com.algaworks.alganews.common.domain.EntityNotFoundException;

public class CashFlowEntryNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	
	public CashFlowEntryNotFoundException(Long id) {
		super(String.format("Não existe um lançamento com o código %s", id));
	}
	
}
