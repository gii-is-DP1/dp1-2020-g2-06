package org.springframework.samples.contraint.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.constraint.EmailConstraint;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
	@Override
	public void initialize(EmailConstraint contactNumber) {
	}
	@Override
	public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
	return emailField != null  && (emailField.matches(".*@us.es$") || emailField.matches(".*@alum.us.es$"));
	}
	
	
}