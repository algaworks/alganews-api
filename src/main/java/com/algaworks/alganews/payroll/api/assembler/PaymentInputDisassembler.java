package com.algaworks.alganews.payroll.api.assembler;

import com.algaworks.alganews.payroll.api.model.PaymentInput;
import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentInputDisassembler {
	
	private final ModelMapper modelMapper;
	
	public Payment toDomainObject(PaymentInput paymentInput) {
		return modelMapper.map(paymentInput, Payment.class);
	}
	
}
