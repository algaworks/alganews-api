package com.algaworks.alganews.payroll.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.alganews.common.api.model.BankAccountModel;
import com.algaworks.alganews.common.api.model.PeriodModel;
import com.algaworks.alganews.users.api.model.UserMinimalModel;

@Getter
@Setter
public class PaymentPreviewModel {
	
	private PeriodModel accountingPeriod;
	
	private UserMinimalModel payee;
	
	private PaymentEarningsModel earnings;
	
	private List<BonusModel> bonuses;
	
	private BigDecimal grandTotalAmount;
	
	private BankAccountModel bankAccount;
	
}
