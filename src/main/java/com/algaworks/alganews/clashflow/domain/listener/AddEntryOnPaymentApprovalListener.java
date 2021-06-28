package com.algaworks.alganews.clashflow.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.clashflow.domain.service.CashFlowEntryCrudService;
import com.algaworks.alganews.payroll.domain.event.PaymentApprovedEvent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AddEntryOnPaymentApprovalListener {
	
	private final CashFlowEntryCrudService entryCrudService;
	
	@EventListener
	public void onPaymentApproved(PaymentApprovedEvent event) {
		entryCrudService.createFrom(event.getPayment());
	}
	
}
