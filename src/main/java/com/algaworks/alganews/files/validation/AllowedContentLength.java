package com.algaworks.alganews.files.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedContentLengthValidator.class)
@Documented
public @interface AllowedContentLength {
	
	String message() default "{AllowedContentLengthValidator.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}