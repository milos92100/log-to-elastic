package com.logtoelastic.api.request.validation.annotation;

import com.logtoelastic.api.request.validation.validator.NotNullOrEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotNullOrEmptyValidator.class})
public @interface NotNullOrEmpty {
    String message() default "Value is null or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
