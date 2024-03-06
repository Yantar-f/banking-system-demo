package com.yantar.bankingsystem.service.impl;

import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.model.UserLoginCredentials;
import com.yantar.bankingsystem.repository.SessionRepository;
import com.yantar.bankingsystem.repository.UserRepository;
import com.yantar.bankingsystem.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public SessionEntity createSession(UserLoginCredentials loginCredentials) {
        return null;
    }

    @Override
    public SessionEntity refreshSession(String refreshToken) {
        return null;
    }

    @Override
    public SessionEntity deleteSession(String refreshToken) {
        return null;
    }
}
