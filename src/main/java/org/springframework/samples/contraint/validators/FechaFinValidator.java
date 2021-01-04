package org.springframework.samples.contraint.validators;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.constraint.FechaFinConstraint;

public class FechaFinValidator implements ConstraintValidator<FechaFinConstraint, LocalDateTime>{

	public void initialize(FechaFinConstraint contactNumber) {
	}
	
	@Override
	public boolean isValid(LocalDateTime dateField, ConstraintValidatorContext context) {
		return dateField != null && (dateField.isAfter(LocalDateTime.now()));
	}

	
	
}
