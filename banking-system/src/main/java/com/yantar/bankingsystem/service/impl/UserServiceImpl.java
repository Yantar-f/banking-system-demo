package com.yantar.bankingsystem.service.impl;

import com.yantar.bankingsystem.config.Role;
import com.yantar.bankingsystem.entity.AccountEntity;
import com.yantar.bankingsystem.entity.EmailEntity;
import com.yantar.bankingsystem.entity.PhoneNumberEntity;
import com.yantar.bankingsystem.entity.RoleEntity;
import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.exception.ContactInfoNotProvidedException;
import com.yantar.bankingsystem.exception.EmailOccupiedException;
import com.yantar.bankingsystem.exception.LastContactInfoRemainingException;
import com.yantar.bankingsystem.exception.PhoneNumberOccupiedException;
import com.yantar.bankingsystem.exception.UserNotFoundException;
import com.yantar.bankingsystem.model.UserCreationData;
import com.yantar.bankingsystem.model.UserSearchCriteria;
import com.yantar.bankingsystem.repository.EmailRepository;
import com.yantar.bankingsystem.repository.PhoneNumberRepository;
import com.yantar.bankingsystem.repository.UserRepository;
import com.yantar.bankingsystem.service.UserService;
import com.yantar.bankingsystem.util.UserSpecBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserSpecBuilder userSpecBuilder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailRepository emailRepository,
                           PhoneNumberRepository phoneNumberRepository,
                           UserSpecBuilder userSpecBuilder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailRepository = emailRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.userSpecBuilder = userSpecBuilder;
    }

    @Override
    public Page<UserEntity> getUsersBy(UserSearchCriteria criteria) {
        int offset = criteria.pageNumber().orElse(0);

        Pageable pageable = criteria.sortingField()
                .map(field -> PageRequest.of(offset, 15, Sort.by(field)))
                .orElse(PageRequest.of(offset, 15));

        return getPageBy(criteria, pageable);
    }

    private Page<UserEntity> getPageBy(UserSearchCriteria criteria, Pageable pageable) {
        Specification<UserEntity> specification = userSpecBuilder.specFromSearchCriteria(criteria);
        Page<UserEntity> page = userRepository.findAll(specification, pageable);

        if (page.isEmpty())
            throw new UserNotFoundException(criteria);

        return page;
    }

    @Override
    public UserEntity createUser(UserCreationData creationData) {
        checkContactInfoPresence(creationData);
        checkContactInfoUniqueness(creationData);

        UserEntity newUser = constructUserFrom(creationData);

        return userRepository.save(newUser);
    }

    @Override
    public EmailEntity addEmail(String address, Long userId) {
        if (isEmailExists(address))
            throw new EmailOccupiedException(address);

        EmailEntity email = new EmailEntity(address, UserEntity.asReference(userId));

        return emailRepository.save(email);
    }

    private boolean isEmailExists(String address) {
        return emailRepository.existsByAddress(address);
    }

    @Override
    public PhoneNumberEntity addPhoneNumber(String number, Long userId) {
        if (isPhoneNumberExists(number))
            throw new PhoneNumberOccupiedException(number);

        PhoneNumberEntity phoneNumber = new PhoneNumberEntity(number, UserEntity.asReference(userId));

        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void deleteEmail(String address, Long userId) {
        if (isOneContactInfoRemaining(userId))
            throw new LastContactInfoRemainingException();

        emailRepository.deleteByAddressAndUser_Id(address, userId);
    }

    @Override
    public void deleteNumber(String number, Long userId) {
        if (isOneContactInfoRemaining(userId))
            throw new LastContactInfoRemainingException();

        phoneNumberRepository.deleteByNumberAndUser_Id(number, userId);
    }

    private boolean isOneContactInfoRemaining(Long userId) {
        return contactInfoCountFor(userId) > 1;
    }

    private int contactInfoCountFor(Long userId) {
        return emailRepository.countByUser_Id(userId) + phoneNumberRepository.countByUser_Id(userId);
    }

    private boolean isPhoneNumberExists(String number) {
        return phoneNumberRepository.existsByNumber(number);
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

        newUser.addRoles(Arrays.stream(creationData.roles()).map(role -> new RoleEntity(Role.valueOf(role))).toList());

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
