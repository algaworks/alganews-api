package com.algaworks.alganews.payroll.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.payroll.domain.repository.PaymentRepository;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.service.UserCrudService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PaymentApprovalService {
	
	private final PaymentCrudService paymentCrudService;
	private final PaymentRepository paymentRepository;
	private final UserCrudService userCrudService;
	private final AlgaSecurity algaSecurity;
	
	@Transactional
	public void approve(Long paymentId) {
		User currentUser = userCrudService.findByIdOrFail(algaSecurity.getUserId());
		
		Payment payment = paymentCrudService.findByIdOrFail(paymentId);
		payment.approve(currentUser);
		paymentRepository.save(payment);
	}
	
	@Transactional
	public void approveAll(Set<Long> paymentIds) {
		User currentUser = userCrudService.findByIdOrFail(algaSecurity.getUserId());
		
		List<Payment> payments = paymentRepository.findAllById(paymentIds);
		payments.forEach(payment -> payment.approve(currentUser));
		
		paymentRepository.saveAll(payments);
	}
	
	
}
