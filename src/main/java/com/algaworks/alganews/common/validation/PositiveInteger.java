package com.algaworks.alganews.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Pattern(regexp = "[\\s]*[0-9]*[0-9]+", message = "{PositiveIntegers.message}")
@Documented
@Constraint(validatedBy = {})
public @interface PositiveInteger {

	String message() default "{PositiveIntegers.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
