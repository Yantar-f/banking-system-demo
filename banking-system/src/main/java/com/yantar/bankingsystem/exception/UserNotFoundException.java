package com.yantar.bankingsystem.exception;

import com.yantar.bankingsystem.model.UserSearchCriteria;

import java.util.HashMap;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UserSearchCriteria criteria) {
        super("user with params not found. Params: " + new HashMap<>() {{
            if (criteria.name().isPresent())
                put("name", criteria.name().get());

            if (criteria.surname().isPresent())
                put("surname", criteria.surname().get());

            if (criteria.patronymic().isPresent())
                put("patronymic", criteria.patronymic().get());

            if (criteria.birthdate().isPresent())
                put("birthdate", criteria.birthdate().get());

            if (criteria.email().isPresent())
                put("email", criteria.email().get());

            if (criteria.phoneNumber().isPresent())
                put("phoneNumber", criteria.phoneNumber().get());

            if (criteria.pageNumber().isPresent())
                put("pageNumber", criteria.pageNumber().get());
        }});
    }
}
