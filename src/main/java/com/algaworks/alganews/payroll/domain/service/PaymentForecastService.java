package com.algaworks.alganews.payroll.domain.service;

import java.util.List;

import com.algaworks.alganews.users.domain.service.UserCrudService;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.payroll.domain.model.Period;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentForecastService {
	
	private final PostRepository postRepository;
	private final UserCrudService userCrudService;
	
	public Payment forecast(Payment payment) {
		payment.setPayee(userCrudService.findByIdOrFail(payment.getPayee().getId()));
		
		List<Post> unpaidPosts = findUnpaidPosts(payment.getAccountingPeriod(), payment.getPayee());
		
		if (unpaidPosts.isEmpty()) {
			throw new BusinessException("Não existem posts neste período para o editor");
		}
		
		payment.setPosts(unpaidPosts);
		payment.calculateGrandTotalAmount();
		
		return payment;
	}
	
	private List<Post> findUnpaidPosts(Period accountingPeriod, User payee) {
		return postRepository.findUnpaid(accountingPeriod.getStartsOn(), accountingPeriod.getEndsOn(), payee.getId());
	}
	
}
