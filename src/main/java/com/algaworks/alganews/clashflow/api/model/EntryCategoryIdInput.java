package com.algaworks.alganews.clashflow.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryCategoryIdInput {
	
	@NotNull
	private Long id;
	
}
