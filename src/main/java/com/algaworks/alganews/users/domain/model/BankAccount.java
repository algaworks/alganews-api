package com.algaworks.alganews.users.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

	@Column(name = "bank_account_bank_code")
	private String bankCode;
	
	@Column(name = "bank_account_agency")
	private String agency;
	
	@Column(name = "bank_account_number")
	private String number;
	
	@Column(name = "bank_account_digit")
	private String digit;
	
	@Column(name = "bank_account_type")
	@Enumerated(EnumType.STRING)
	private BankAccountType type;
	
}
