package com.algaworks.alganews.payroll.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.algaworks.alganews.common.api.model.PeriodModel;
import com.algaworks.alganews.users.api.model.UserMinimalModel;

@Getter
@Setter
public class PaymentSummaryModel {
	
	private Long id;
	
	private BigDecimal grandTotalAmount;
	
	private PeriodModel accountingPeriod;
	
	private UserMinimalModel payee;
	
	private OffsetDateTime approvedAt;
	
	private LocalDate scheduledTo;
	
	private boolean canBeDeleted;
	
	private boolean canBeApproved;
	
}
