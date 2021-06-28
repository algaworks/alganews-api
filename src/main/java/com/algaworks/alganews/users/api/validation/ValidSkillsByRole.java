package com.algaworks.alganews.users.api.validation;

import com.algaworks.alganews.users.domain.model.Role;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSkillsByRoleValidator.class)
@Documented
public @interface ValidSkillsByRole {
	
	String message() default "{ValidSkillsByRole.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	int uniqueSkillCount() default 3;
	
	Role role() default Role.EDITOR;
	
	
}