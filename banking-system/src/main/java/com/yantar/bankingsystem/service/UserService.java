package com.yantar.bankingsystem.service;

import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.model.UserCreationData;
import com.yantar.bankingsystem.model.UserSearchCriteria;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserEntity> getUsersBy(UserSearchCriteria criteria);
    UserEntity createUser(UserCreationData creationData);
}
