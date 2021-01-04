package org.springframework.samples.contraint.validators;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.constraint.FechaFinConstraint;

public class FechaFinValidator implements ConstraintValidator<FechaFinConstraint, LocalDate>{

	public void initialize(FechaFinConstraint contactNumber) {
	}
	
	@Override
	public boolean isValid(LocalDate dateField, ConstraintValidatorContext context) {
		return dateField != null && (dateField.isAfter(LocalDate.now()));
	}

	
	
}
