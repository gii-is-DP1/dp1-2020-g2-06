package org.springframework.samples.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.samples.contraint.validators.FechaFinValidator;

@Documented
@Constraint(validatedBy = FechaFinValidator.class)
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaFinConstraint {
	String message() default "Esta fecha no puede ser anterior a la fecha de hoy";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
