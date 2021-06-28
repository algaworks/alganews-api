package com.algaworks.alganews.payroll.domain.exception;

import com.algaworks.alganews.common.domain.EntityNotFoundException;

public class PaymentNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException(Long id) {
		super(String.format("Não existe um pagamento com o código %s", id));
	}
	
}
