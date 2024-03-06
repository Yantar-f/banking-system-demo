package com.yantar.bankingsystem.util;

import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.model.UserSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpecBuilder {
    Specification<UserEntity> specFromIdentifier(String identifier);
    Specification<UserEntity> specFromSearchCriteria(UserSearchCriteria criteria);
}
