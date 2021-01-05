package org.springframework.samples.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.samples.constraint.validators.EmailValidator;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
String message() default "Email incorrecto. Debe ser un email @us.es o @alum.us.es";
Class<?>[] groups() default {};
Class<? extends Payload>[] payload() default {};
}
