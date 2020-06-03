package io.github.listaction.shared;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CustomFieldValidator implements ConstraintValidator<ValidCustomField, String> {

    @Override
    public void initialize(ValidCustomField constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ("".equals(value) || value == null) {
            return false;
        }

        if ("correct_value".equalsIgnoreCase(value)){
            return true;
        }

        return false;
    }

}
