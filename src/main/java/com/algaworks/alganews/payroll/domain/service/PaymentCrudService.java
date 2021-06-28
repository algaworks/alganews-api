package com.algaworks.alganews.payroll.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.payroll.domain.exception.PaymentNotFoundException;
import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.payroll.domain.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentCrudService {
	
	private final PaymentRepository paymentRepository;
	private final PaymentForecastService paymentForecastService;
	
	public Payment findByIdOrFail(Long id) {
		return paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException(id));
	}
	
	@Transactional
	public Payment create(Payment payment) {
		payment = paymentForecastService.forecast(payment);
		
		payment.getPosts().forEach(post -> post.setPaid(true));
		payment = paymentRepository.save(payment);
		
		return payment;
	}
	
	@Transactional
	public void delete(Long paymentId) {
		Payment payment = findByIdOrFail(paymentId);
		
		if (payment.isApproved()) {
			throw new BusinessException("Não é possível excluir um pagamento já aprovado");
		}
		payment.getPosts().forEach(post -> post.setPaid(false));
		payment.setPosts(null);
		
		paymentRepository.delete(payment);
	}
	
}
