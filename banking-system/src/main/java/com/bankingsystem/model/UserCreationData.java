package com.bankingsystem.model;

import com.bankingsystem.config.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public record UserCreationData(
        @NotEmpty
        String name,

        @NotEmpty
        String surname,

        @NotEmpty
        String patronymic,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate birthdate,

        @NotEmpty
        String password,

        Optional<String> email,

        @PhoneNumber
        Optional<String> phoneNumber,

        @NotNull
        @Min(0)
        Long accountAmount
) {

}
