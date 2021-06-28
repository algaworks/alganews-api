package com.algaworks.alganews.payroll.api.model;

import com.algaworks.alganews.payroll.api.validation.FuturePaymentPeriod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import com.algaworks.alganews.common.api.model.PeriodInput;
import com.algaworks.alganews.users.api.model.UserIdInput;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PaymentInput {

	@NotNull
	private UserIdInput payee;
	
	@Valid
	@NotNull
	private PeriodInput accountingPeriod;

	@NotNull
	@FuturePaymentPeriod
	private LocalDate scheduledTo;
	
	@Nullable
	@Valid
	private List<BonusInput> bonuses;

}
