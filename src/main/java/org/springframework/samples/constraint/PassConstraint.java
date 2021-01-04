package org.springframework.samples.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.samples.contraint.validators.PassValidator;

@Documented
@Constraint(validatedBy = PassValidator.class)
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassConstraint {
	String message() default "La contraseña debe tener una longitud minima de 8, una mayuscula, un número y un caracter especial";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
