package com.algaworks.alganews.posts.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PostEarningsModel {
	
	private BigDecimal pricePerWord = BigDecimal.ZERO;
	
	private Integer words = 0;
	
	private BigDecimal totalAmount = BigDecimal.ZERO;

}
