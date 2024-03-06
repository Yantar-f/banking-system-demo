package com.yantar.bankingsystem.model;

import com.yantar.bankingsystem.config.Role;
import com.yantar.bankingsystem.entity.SessionEntity;

import java.util.Set;

public record SessionDTO(
        Long userId,
        Set<Role> roles
) {
    public SessionDTO(SessionEntity session) {
        this(session.getUserId(), session.getRoles());
    }
}
