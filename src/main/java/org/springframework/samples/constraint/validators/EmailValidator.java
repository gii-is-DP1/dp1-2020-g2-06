package org.springframework.samples.constraint.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.constraint.EmailConstraint;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.TutorService;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	TutorService tutorService;
	
	@Override
	public void initialize(EmailConstraint contactNumber) {
	}
	@Override
	public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
	return emailField != null  && (emailField.matches(".*@us.es$") || emailField.matches(".*@alum.us.es$"));
	}
	
	
}