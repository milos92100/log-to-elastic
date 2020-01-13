package com.logtoelastic.api.request.validation.validator;

import com.logtoelastic.api.request.validation.annotation.NotNullOrEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullOrEmptyValidator implements ConstraintValidator<NotNullOrEmpty, String> {
    @Override
    public void initialize(NotNullOrEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && !value.trim().isEmpty();
    }
}
