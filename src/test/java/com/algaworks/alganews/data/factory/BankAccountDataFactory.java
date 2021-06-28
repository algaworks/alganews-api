package com.algaworks.alganews.data.factory;

import com.algaworks.alganews.users.domain.model.BankAccount;
import com.algaworks.alganews.users.domain.model.BankAccountType;

public class BankAccountDataFactory {
	
	private BankAccountDataFactory() { }
	
	public static BankAccount createDefault() {
		return BankAccount.builder()
				.agency("0001")
				.bankCode("001")
				.number("123456")
				.digit("5")
				.type(BankAccountType.SAVING)
				.build();
	}
}
