package com.algaworks.alganews.payroll.domain.event;

import com.algaworks.alganews.payroll.domain.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentApprovedEvent {
	
	private final Payment payment;
	
}
