package com.algaworks.alganews.posts.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
public class PostEarnings {
	
	@Builder.Default
	@Column(name = "earnings_price_per_word")
	private BigDecimal pricePerWord = BigDecimal.ZERO;
	
	@Builder.Default
	@Column(name = "earnings_words")
	private Integer words = 0;
	
	@Builder.Default
	@Column(name = "earnings_total_amount")
	private BigDecimal totalAmount = BigDecimal.ZERO;

}
