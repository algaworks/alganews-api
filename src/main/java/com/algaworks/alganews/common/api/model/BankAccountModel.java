package com.algaworks.alganews.common.api.model;

import com.algaworks.alganews.common.validation.PositiveInteger;
import com.algaworks.alganews.users.domain.model.BankAccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BankAccountModel {
	
	@NotBlank
	@Size(min = 3, max = 3)
	@PositiveInteger
	private String bankCode;
	
	@NotBlank
	@Size(min = 1, max = 10)
	@PositiveInteger
	private String agency;
	
	@NotBlank
	@Size(min = 1, max = 20)
	@PositiveInteger
	private String number;
	
	@NotBlank
	@Size(min = 1, max = 1)
	@PositiveInteger
	private String digit;
	
	@NotNull
	private BankAccountType type;

}
