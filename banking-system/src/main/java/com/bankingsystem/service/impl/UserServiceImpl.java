package com.bankingsystem.service.impl;

import com.bankingsystem.entity.AccountEntity;
import com.bankingsystem.entity.EmailEntity;
import com.bankingsystem.entity.PhoneNumberEntity;
import com.bankingsystem.entity.UserEntity;
import com.bankingsystem.exception.ContactInfoNotProvidedException;
import com.bankingsystem.exception.EmailOccupiedException;
import com.bankingsystem.exception.PhoneNumberOccupiedException;
import com.bankingsystem.model.UserCreationData;
import com.bankingsystem.model.UserSearchCriteria;
import com.bankingsystem.repository.EmailRepository;
import com.bankingsystem.repository.PhoneNumberRepository;
import com.bankingsystem.repository.UserRepository;
import com.bankingsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailRepository emailRepository,
                           PhoneNumberRepository phoneNumberRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailRepository = emailRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public Page<UserEntity> getUsersBy(UserSearchCriteria criteria) {
        return null;
    }

    @Override
    public UserEntity createUser(UserCreationData creationData) {
        checkContactInfoPresence(creationData);
        checkContactInfoUniqueness(creationData);

        UserEntity newUser = constructUserFrom(creationData);

        return userRepository.save(newUser);
    }

    private void checkContactInfoUniqueness(UserCreationData creationData) {
        creationData.email().ifPresent(address -> {
            if (emailRepository.existsByAddress(address))
                throw new EmailOccupiedException(address);
        });

        creationData.phoneNumber().ifPresent(number -> {
            if (phoneNumberRepository.existsByNumber(number))
                throw new PhoneNumberOccupiedException(number);
        });
    }

    private UserEntity constructUserFrom(UserCreationData creationData) {
        String encodedPassword = encodePassword(creationData.password());

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

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkContactInfoPresence(UserCreationData creationData) {
        if (creationData.email().isEmpty() && creationData.phoneNumber().isEmpty())
            throw new ContactInfoNotProvidedException();
    }
}
