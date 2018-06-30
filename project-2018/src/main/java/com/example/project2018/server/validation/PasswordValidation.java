package com.example.project2018.server.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.*;

public class PasswordValidation implements ConstraintValidator<ValidPassword, String>{

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
		           new LengthRule(8, 30), 
		           new UppercaseCharacterRule(1), 
		           new DigitCharacterRule(1), 
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
