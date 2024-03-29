package com.yantar.bankingsystem.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserRolesValidator.class)
public @interface UserRoles {
    String message() default "Param should be a user role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
