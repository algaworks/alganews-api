package com.algaworks.alganews.payroll.api.assembler;

import com.algaworks.alganews.users.api.assembler.UserMinimalModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.payroll.api.model.PaymentPreviewModel;
import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentPreviewAssembler {
	
	private final ModelMapper modelMapper;
	private final UserMinimalModelAssembler userMinimalModelAssembler;
	
	public PaymentPreviewModel toModel(Payment payment) {
		PaymentPreviewModel paymentPreviewModel = modelMapper.map(payment, PaymentPreviewModel.class);
		paymentPreviewModel.setPayee(userMinimalModelAssembler.toModel(payment.getPayee()));
		return paymentPreviewModel;
	}
	
}
