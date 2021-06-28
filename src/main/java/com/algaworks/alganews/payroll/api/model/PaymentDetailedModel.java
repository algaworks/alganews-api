package com.algaworks.alganews.payroll.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import com.algaworks.alganews.common.api.model.BankAccountModel;
import com.algaworks.alganews.common.api.model.PeriodModel;
import com.algaworks.alganews.users.api.model.UserMinimalModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailedModel {
	
	private Long id;
	private PeriodModel accountingPeriod;
	private PaymentEarningsModel earnings;
	private List<BonusModel> bonuses;
	private BigDecimal grandTotalAmount;
	private BankAccountModel bankAccount;
	private LocalDate scheduledTo;
	
	private UserMinimalModel payee;
	private UserMinimalModel createdBy;
	private UserMinimalModel approvedBy;
	private UserMinimalModel updatedBy;
	
	private OffsetDateTime createdAt;
	private OffsetDateTime approvedAt;
	private OffsetDateTime updatedAt;
	
	private boolean canBeDeleted;
	private boolean canBeApproved;
	
}
