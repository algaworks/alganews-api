package com.algaworks.alganews.payroll.api.validation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FuturePaymentPeriodValidator
		implements ConstraintValidator<FuturePaymentPeriod, LocalDate> {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
	
	public final boolean isValid(final LocalDate scheduledTo,
								 final ConstraintValidatorContext context) {
		
		if (scheduledTo == null) {
			return true;
		}
		
		LocalDate startDate = LocalDate.now().plusDays(1);
		LocalDate endDate = LocalDate.now().plusDays(7);
		
		boolean isValid = (scheduledTo.isAfter(startDate) || scheduledTo.isEqual(startDate))
				&& (scheduledTo.isBefore(endDate) || scheduledTo.isEqual(endDate));
		
		if (!isValid) {
			((ConstraintValidatorContextImpl) context)
					.addMessageParameter("startDate", startDate.format(formatter))
					.addMessageParameter("endDate", endDate.format(formatter));
		}
		
		return isValid;
	}
}

