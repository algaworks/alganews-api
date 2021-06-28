package com.algaworks.alganews.metrics.api.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditorTagRatioProjection {
	
	private String tagName;
	private BigDecimal percentage;
	
}
