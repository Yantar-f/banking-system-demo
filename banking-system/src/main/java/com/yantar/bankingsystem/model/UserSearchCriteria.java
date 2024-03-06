package com.yantar.bankingsystem.model;

import com.yantar.bankingsystem.config.UserSortingField;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserSearchCriteria(
        Optional<String> name,
        Optional<String> surname,
        Optional<String> patronymic,

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        Optional<LocalDate> birthdate,

        Optional<String> email,
        Optional<String> phoneNumber,

        @UserSortingField
        Optional<String> sortingField,

        Optional<Integer> pageNumber
){
}
