package com.yantar.bankingsystem.service;

import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.model.UserLoginCredentials;

public interface AuthService {
    SessionEntity   createSession   (UserLoginCredentials loginCredentials);
    SessionEntity   refreshSession  (String refreshToken);
    void            deleteSession   (String refreshToken);
}
