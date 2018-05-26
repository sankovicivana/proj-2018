package com.example.project2018.server.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.*;

public class PasswordValidation implements ConstraintValidator<ValidPassword, String>{

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
		           new LengthRule(8, 30), 
		           new UppercaseCharacterRule(1), 
		           new DigitCharacterRule(1), 
		           new SpecialCharacterRule(1), 
		           new NumericalSequenceRule(3,false), 
		           new AlphabeticalSequenceRule(3,false), 
		           new QwertySequenceRule(3,false),
		           new WhitespaceRule()));
		 
		        RuleResult result = validator.validate(new PasswordData(password));
		        if (result.isValid()) {
		            return true;
		        }
		        context.disableDefaultConstraintViolation();
		        context.buildConstraintViolationWithTemplate(
		        		String.join(", ", validator.getMessages(result)))
		          .addConstraintViolation();
		        return false;
	}

}
