package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.config.AccessTokenConfig;
import com.yantar.bankingsystem.config.RefreshTokenConfig;
import com.yantar.bankingsystem.config.TokenConfig;
import com.yantar.bankingsystem.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtilImpl implements CookieUtil {
    private final AccessTokenConfig accessTokenConfig;
    private final RefreshTokenConfig refreshTokenConfig;

    public CookieUtilImpl(AccessTokenConfig accessTokenConfig, RefreshTokenConfig refreshTokenConfig) {
        this.accessTokenConfig = accessTokenConfig;
        this.refreshTokenConfig = refreshTokenConfig;
    }

    @Override
    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return createTokenCookie(accessToken, accessTokenConfig);
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return createTokenCookie(refreshToken, refreshTokenConfig);
    }

    @Override
    public ResponseCookie createAccessTokenCleaningCookie() {
        return createTokenCleaningCookie(accessTokenConfig);
    }

    @Override
    public ResponseCookie createRefreshTokenCleaningCookie() {
        return createTokenCleaningCookie(refreshTokenConfig);
    }

    @Override
    public Optional<String> extractAccessTokenFromCookies(Cookie[] cookies) {
        return extractTokenFromCookies(cookies, accessTokenConfig);
    }

    @Override
    public Optional<String> extractRefreshTokenFromCookies(Cookie[] cookies) {
        return extractTokenFromCookies(cookies, refreshTokenConfig);
    }

    private Optional<String> extractTokenFromCookies(Cookie[] cookies, TokenConfig tokenConfig) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenConfig.getCookieName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private ResponseCookie createTokenCleaningCookie(TokenConfig config) {
        return createCleaningCookie(config.getCookieName(), config.getCookiePath());
    }

    private ResponseCookie createCleaningCookie(String name, String path) {
        return ResponseCookie.from(name)
                .path(path)
                .maxAge(0)
                .build();
    }

    private ResponseCookie createTokenCookie(String token, TokenConfig config) {
        return ResponseCookie.from(config.getCookieName())
                .value(token)
                .path(config.getCookiePath())
                .maxAge(config.getCookieExpirationS())
                .httpOnly(true)
                .build();
    }
}
