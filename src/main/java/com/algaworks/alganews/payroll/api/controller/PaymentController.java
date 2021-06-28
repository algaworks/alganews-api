package com.algaworks.alganews.payroll.api.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.payroll.domain.exception.PaymentNotFoundException;
import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.users.domain.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.payroll.api.assembler.PaymentDetailedAssembler;
import com.algaworks.alganews.payroll.api.assembler.PaymentInputDisassembler;
import com.algaworks.alganews.payroll.api.assembler.PaymentPreviewAssembler;
import com.algaworks.alganews.payroll.api.assembler.PaymentPreviewInputDisassembler;
import com.algaworks.alganews.payroll.api.assembler.PaymentSummaryAssembler;
import com.algaworks.alganews.payroll.api.model.PaymentDetailedModel;
import com.algaworks.alganews.payroll.api.model.PaymentInput;
import com.algaworks.alganews.payroll.api.model.PaymentPreviewInput;
import com.algaworks.alganews.payroll.api.model.PaymentPreviewModel;
import com.algaworks.alganews.payroll.api.model.PaymentSummaryModel;
import com.algaworks.alganews.payroll.domain.filter.PaymentFilter;
import com.algaworks.alganews.payroll.domain.repository.PaymentRepository;
import com.algaworks.alganews.payroll.domain.repository.PaymentSpecs;
import com.algaworks.alganews.payroll.domain.service.PaymentApprovalService;
import com.algaworks.alganews.payroll.domain.service.PaymentCrudService;
import com.algaworks.alganews.payroll.domain.service.PaymentForecastService;
import com.algaworks.alganews.posts.api.assembler.PostWithEarningsAssembler;
import com.algaworks.alganews.posts.api.model.PostWithEarningsModel;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
@Validated
public class PaymentController {
	
	private final PaymentRepository paymentRepository;
	private final PaymentCrudService paymentCrudService;
	private final PaymentApprovalService paymentApprovalService;
	private final PaymentForecastService paymentForecastService;
	
	private final PostWithEarningsAssembler postWithEarningsAssembler;
	private final PaymentDetailedAssembler paymentDetailedAssembler;
	private final PaymentPreviewAssembler paymentPreviewAssembler;
	private final PaymentSummaryAssembler paymentSummaryAssembler;
	private final PaymentInputDisassembler paymentInputDisassembler;
	private final PaymentPreviewInputDisassembler paymentPreviewInputDisassembler;
	
	private final PostRepository postRepository;
	
	@CheckSecurity.Payroll.CanCreateOrEdit
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentDetailedModel create(@Valid @RequestBody PaymentInput input) {
		Payment payment = paymentInputDisassembler.toDomainObject(input);
		
		try {
			payment = paymentCrudService.create(payment);
		} catch (UserNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		return paymentDetailedAssembler.toModel(payment);
	}
	
	@CheckSecurity.Payroll.CanCreateOrEdit
	@PostMapping("/previews")
	public PaymentPreviewModel preview(@Valid @RequestBody PaymentPreviewInput input) {
		Payment payment = paymentPreviewInputDisassembler.toDomainObject(input);
		
		try {
			payment = paymentForecastService.forecast(payment);
		} catch (UserNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		return paymentPreviewAssembler.toModel(payment);
	}
	
	@CheckSecurity.Payroll.CanList
	@GetMapping
	public Page<PaymentSummaryModel> search(
			@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
			PaymentFilter paymentFilter) {
		Page<Payment> payments = paymentRepository.findAll(PaymentSpecs.usingFilter(paymentFilter), pageable);
		List<PaymentSummaryModel> paymentsModel = paymentSummaryAssembler.toCollectionModel(payments.getContent());
		
		return new PageImpl<>(paymentsModel, pageable, payments.getTotalElements());
	}
	
	@CheckSecurity.Payroll.CanList
	@GetMapping("/{paymentId}")
	public PaymentDetailedModel findById(@PathVariable Long paymentId) {
		Payment payment = paymentCrudService.findByIdOrFail(paymentId);
		return paymentDetailedAssembler.toModel(payment);
	}
	
	@CheckSecurity.Payroll.CanList
	@GetMapping("/{paymentId}/posts")
	public List<PostWithEarningsModel> findPostsByPayment(@PathVariable Long paymentId,
			@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Sort sort) {
		if (!paymentRepository.existsById(paymentId)) {
			throw new PaymentNotFoundException(paymentId);
		}
		List<Post> posts = postRepository.findAllByPaymentId(paymentId, sort);
		return postWithEarningsAssembler.toCollectionModel(posts);
	}
	
	@CheckSecurity.Payroll.CanApprove
	@PutMapping("/{paymentId}/approval")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void approve(@PathVariable Long paymentId) {
		paymentApprovalService.approve(paymentId);
	}
	
	@CheckSecurity.Payroll.CanApprove
	@PutMapping("/bulk-approvals")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void approveAll(@RequestBody @Valid @NotEmpty Set<Long> paymentIds) {
		paymentApprovalService.approveAll(paymentIds);
	}
	
	@CheckSecurity.Payroll.CanCreateOrEdit
	@DeleteMapping("/{paymentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentId) {
		paymentCrudService.delete(paymentId);
	}
	
}
