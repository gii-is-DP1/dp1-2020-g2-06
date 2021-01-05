package org.springframework.samples.constraint.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.constraint.PassConstraint;

public class PassValidator implements ConstraintValidator<PassConstraint, String>{
	
	public void initialize(PassConstraint contactNumber) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		char[] v = value.toCharArray();
		boolean numero = false;
		boolean especial = false;
		boolean mayuscula = false;
		for(int i = 0; i<v.length; i++) {
			char p = v[i];
			if(Character.isDigit(p)) {
				numero = true;
			}else if((!Character.isLetter(p))&(!Character.isDigit(p))) {
				especial = true;
			}else if(Character.isUpperCase(p)) {
				mayuscula = true;
			}
			
		}
		return value!=null && (v.length>=8) && numero && especial && mayuscula;
	}

}
