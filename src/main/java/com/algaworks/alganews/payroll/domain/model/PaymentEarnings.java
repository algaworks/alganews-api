package com.algaworks.alganews.payroll.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.algaworks.alganews.posts.domain.model.PostEarnings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEarnings {
	
	@Builder.Default
	@Column(name = "earnings_words")
	private Integer words = 0;
	
	@Builder.Default
	@Column(name = "earnings_total_amount")
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	public void addPostEarnings(PostEarnings postEarnings) {
		this.words += postEarnings.getWords();
		this.totalAmount = this.totalAmount.add(postEarnings.getTotalAmount());
	}
	
}
