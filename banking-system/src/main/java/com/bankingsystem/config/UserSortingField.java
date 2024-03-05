package com.bankingsystem.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserSortingFieldValidator.class)
public @interface UserSortingField {
    String message() default "Param should be a sorting field name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
