package com.bankingsystem.model;

import com.bankingsystem.entity.EmailEntity;
import com.bankingsystem.entity.PhoneNumberEntity;
import com.bankingsystem.entity.UserEntity;

import java.time.LocalDate;

public record UserDTO (
    Long id,
    String name,
    String surname,
    String patronymic,
    LocalDate birthdate,
    String[] emails,
    String[] phoneNumbers,
    Long accountAmount
) {
    public UserDTO(UserEntity user) {
        this(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getBirthdate(),
                user.getEmails().stream().map(EmailEntity::getAddress).toArray(String[]::new),
                user.getPhoneNumbers().stream().map(PhoneNumberEntity::getNumber).toArray(String[]::new),
                user.getAccount().getAmount()
        );
    }
}
