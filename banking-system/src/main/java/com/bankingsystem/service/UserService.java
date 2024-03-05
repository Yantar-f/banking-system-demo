package com.bankingsystem.service;

import com.bankingsystem.entity.UserEntity;
import com.bankingsystem.model.UserCreationData;
import com.bankingsystem.model.UserSearchCriteria;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserEntity> getUsersBy(UserSearchCriteria criteria);
    UserEntity createUser(UserCreationData creationData);
}
