package com.algaworks.alganews.payroll.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.api.assembler.UserMinimalModelAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.payroll.api.model.PaymentSummaryModel;
import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PaymentSummaryAssembler {
	
	private final ModelMapper modelMapper;
	private final UserMinimalModelAssembler userMinimalModelAssembler;
	private final AlgaSecurity algaSecurity;
	
	public PaymentSummaryModel toModel(Payment payment) {
		PaymentSummaryModel paymentSummaryModel = modelMapper.map(payment, PaymentSummaryModel.class);
		paymentSummaryModel.setPayee(userMinimalModelAssembler.toModel(payment.getPayee()));
		paymentSummaryModel.setCanBeApproved(algaSecurity.canPaymentBeApproved(payment));
		return paymentSummaryModel;
	}
	
	public List<PaymentSummaryModel> toCollectionModel(List<Payment> payments) {
		return payments.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
