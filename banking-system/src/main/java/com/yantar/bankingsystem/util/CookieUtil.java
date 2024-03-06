package com.yantar.bankingsystem.util;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface CookieUtil {
    ResponseCookie createAccessTokenCookie          (String accessToken);
    ResponseCookie createRefreshTokenCookie         (String refreshToken);

    ResponseCookie createAccessTokenCleaningCookie();
    ResponseCookie createRefreshTokenCleaningCookie();

    Optional<String> extractAccessTokenFromCookies        (Cookie[] cookies);
    Optional<String> extractRefreshTokenFromCookies       (Cookie[] cookies);
}
