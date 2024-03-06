package com.yantar.bankingsystem.config;

import com.yantar.bankingsystem.entity.UserEntity_;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserSortingFieldValidator implements ConstraintValidator<UserSortingField, Optional<String>> {
    private static final Set<String> USER_SORTING_FIELDS = new HashSet<>() {{
        add(UserEntity_.NAME);
        add(UserEntity_.SURNAME);
        add(UserEntity_.PATRONYMIC);
        add(UserEntity_.BIRTHDATE);
    }};

    @Override
    public boolean isValid(Optional<String> sortingField, ConstraintValidatorContext context) {
        return sortingField
                .map(USER_SORTING_FIELDS::contains)
                .orElse(true);
    }
}
