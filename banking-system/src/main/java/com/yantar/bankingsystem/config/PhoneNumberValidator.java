package com.yantar.bankingsystem.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Optional<String>> {
    @Override
    public boolean isValid(Optional<String> number, ConstraintValidatorContext constraintValidatorContext) {
        return number
                .map(s -> s.length() == 11 && !s.startsWith("0"))
                .orElse(true);
    }
}
