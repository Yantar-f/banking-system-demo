package com.yantar.bankingsystem.model;

import com.yantar.bankingsystem.entity.EmailEntity;
import com.yantar.bankingsystem.entity.PhoneNumberEntity;
import com.yantar.bankingsystem.entity.RoleEntity;
import com.yantar.bankingsystem.entity.UserEntity;

import java.time.LocalDate;

public record UserDTO (
    Long id,
    String name,
    String surname,
    String patronymic,
    LocalDate birthdate,
    String[] emails,
    String[] phoneNumbers,
    Long accountAmount,
    String[] roles
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
                user.getAccount().getAmount(),
                user.getRoles().stream().map(RoleEntity::getDesignation).toArray(String[]::new)
        );
    }
}
