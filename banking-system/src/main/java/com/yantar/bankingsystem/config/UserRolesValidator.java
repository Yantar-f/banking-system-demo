package com.yantar.bankingsystem.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class UserRolesValidator implements ConstraintValidator<UserRoles, String[]>{
    private static final HashSet<String> ROLES_SET = new HashSet<>();

    static {
        for (var role : Role.values())
            ROLES_SET.add(role.name());
    }

    @Override
    public boolean isValid(String[] roles, ConstraintValidatorContext context) {
        if (roles == null)
            return true;

        return ROLES_SET.containsAll(List.of(roles));
    }
}
