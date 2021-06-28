package com.algaworks.alganews.clashflow.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.algaworks.alganews.clashflow.domain.model.EntryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashFlowEntryInput {
	
	@NotNull
	@PastOrPresent
	private LocalDate transactedOn;
	
	@NotNull
	private EntryType type;
	
	@NotBlank
	@Size(max = 255)
	private String description;
	
	@NotNull
	@Valid
	private EntryCategoryIdInput category;
	
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;

}
