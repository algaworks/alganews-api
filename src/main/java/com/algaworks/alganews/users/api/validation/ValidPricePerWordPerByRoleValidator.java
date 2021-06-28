package com.algaworks.alganews.users.api.validation;

import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.domain.model.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ValidPricePerWordPerByRoleValidator
		implements ConstraintValidator<ValidPricePerWordPerByRole, UserInput> {
	
	private static final int LESSER_THAN_CONSTANT = -1;
	private Role requiredRole;
	
	@Override
	public final void initialize(final ValidPricePerWordPerByRole annotation) {
		this.requiredRole = annotation.role();
	}
	
	public final boolean isValid(final UserInput user,
								 final ConstraintValidatorContext context) {
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode("pricePerWord")
				.addConstraintViolation();
		
		return user.getPricePerWord() != null && isEditor(user) || isNotEditor(user);
	}
	
	private boolean isNotEditor(UserInput user) {
		return !isEditor(user);
	}
	
	private boolean isEditor(UserInput user) {
		return requiredRole.equals(user.getRole());
	}
	
	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}

