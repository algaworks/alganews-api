package com.algaworks.alganews.payroll.api.assembler;

import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.api.assembler.UserMinimalModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.payroll.api.model.PaymentDetailedModel;
import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentDetailedAssembler {
	
	private final ModelMapper modelMapper;
	private final UserMinimalModelAssembler userMinimalModelAssembler;
	private final AlgaSecurity algaSecurity;
	
	public PaymentDetailedModel toModel(Payment payment) {
		PaymentDetailedModel paymentDetailedModel = modelMapper.map(payment, PaymentDetailedModel.class);

		paymentDetailedModel.setPayee(userMinimalModelAssembler.toModel(payment.getPayee()));
		paymentDetailedModel.setCanBeApproved(algaSecurity.canPaymentBeApproved(payment));
		
		return paymentDetailedModel;
	}
	
}
