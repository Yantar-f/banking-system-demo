package com.yantar.bankingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenConfig extends TokenConfig {
    public RefreshTokenConfig(
            @Value("${app.security.refresh-token.issuer}") String issuer,
            @Value("${app.security.refresh-token.activation-ms}") long activationMs,
            @Value("${app.security.refresh-token.expiration-ms}") long expirationMs,
            @Value("${app.security.refresh-token.allowed-clock-skew-s}") long allowedClockSkew,
            @Value("${app.security.refresh-token.cookie-name}") String cookieName,
            @Value("${app.security.refresh-token.cookie-path}") String cookiePath,
            @Value("${app.security.refresh-token.cookie-expiration-s}") long cookieExpirationS,
            @Value("${app.security.refresh-token.secret-key}") String secretKey) {

        super(
                issuer,
                activationMs,
                expirationMs,
                allowedClockSkew,
                cookieName,
                cookiePath,
                cookieExpirationS,
                secretKey
        );
    }

    @Override
    public String getTokenTypeName() {
        return "refresh token";
    }
}
