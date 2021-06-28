package com.algaworks.alganews.users.api.validation;

import com.algaworks.alganews.users.domain.model.Role;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPricePerWordPerByRoleValidator.class)
public @interface ValidPricePerWordPerByRole {
	
	String message() default "{ValidPricePerWordPerByRole.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	Role role() default Role.EDITOR;
	
}
