package com.yantar.bankingsystem.service;

import com.yantar.bankingsystem.entity.EmailEntity;
import com.yantar.bankingsystem.entity.PhoneNumberEntity;
import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.model.UserCreationData;
import com.yantar.bankingsystem.model.UserSearchCriteria;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserEntity> getUsersBy(UserSearchCriteria criteria);
    UserEntity createUser(UserCreationData creationData);
    EmailEntity addEmail(String address, Long userId);
    PhoneNumberEntity addPhoneNumber(String number, Long userId);
    void deleteEmail(String address, Long userId);
    void deleteNumber(String number, Long userId);
}
