package com.bankingsystem.service.impl;

import com.bankingsystem.entity.AccountEntity;
import com.bankingsystem.entity.EmailEntity;
import com.bankingsystem.entity.PhoneNumberEntity;
import com.bankingsystem.entity.UserEntity;
import com.bankingsystem.exception.ContactInfoNotProvidedException;
import com.bankingsystem.model.UserCreationData;
import com.bankingsystem.model.UserSearchCriteria;
import com.bankingsystem.repository.UserRepository;
import com.bankingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserEntity> getUsersBy(UserSearchCriteria criteria) {
        return null;
    }

    @Override
    public UserEntity createUser(UserCreationData creationData) {
        UserEntity newUser = constructUserFrom(creationData);
        return userRepository.save(newUser);
    }

    private UserEntity constructUserFrom(UserCreationData creationData) {
        if (creationData.email().isEmpty() && creationData.phoneNumber().isEmpty())
            throw new ContactInfoNotProvidedException();

        String encodedPassword = passwordEncoder.encode(creationData.password());

        UserEntity newUser = new UserEntity(
                creationData.name(),
                creationData.surname(),
                creationData.patronymic(),
                creationData.birthdate(),
                encodedPassword
        );

        creationData.email()
                .ifPresent(address -> newUser.addEmail(new EmailEntity(address, newUser)));

        creationData.phoneNumber()
                .ifPresent(number -> newUser.addPhoneNumber(new PhoneNumberEntity(number, newUser)));

        newUser.setAccount(new AccountEntity(creationData.accountAmount(), newUser));

        return newUser;
    }
}
