package com.yantar.bankingsystem.controller;

import com.yantar.bankingsystem.config.RefreshTokenConfig;
import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.exception.MissingTokenException;
import com.yantar.bankingsystem.model.SessionDTO;
import com.yantar.bankingsystem.model.UserLoginCredentials;
import com.yantar.bankingsystem.service.AuthService;
import com.yantar.bankingsystem.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final RefreshTokenConfig refreshTokenConfig;

    public AuthController(AuthService authService,
                          CookieUtil cookieUtil,
                          RefreshTokenConfig refreshTokenConfig) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
        this.refreshTokenConfig = refreshTokenConfig;
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDTO> createSession(UserLoginCredentials loginCredentials) {
        SessionEntity session = authService.createSession(loginCredentials);

        return ResponseEntity.ok()
                .header(SET_COOKIE, createAccessTokenCookie(session.getAccessToken()))
                .header(SET_COOKIE, createRefreshTokenCookie(session.getRefreshToken()))
                .body(new SessionDTO(session));
    }

    @PatchMapping("/sessions")
    public ResponseEntity<SessionDTO> refreshSession(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromRequest(request);
        SessionEntity session = authService.refreshSession(refreshToken);

        return ResponseEntity.ok()
                .header(SET_COOKIE, createAccessTokenCookie(session.getAccessToken()))
                .header(SET_COOKIE, createRefreshTokenCookie(session.getRefreshToken()))
                .build();
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<Void> deleteSession(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromRequest(request);

        authService.deleteSession(refreshToken);

        return ResponseEntity.ok()
                .header(SET_COOKIE, createAccessTokenCleaningCookie())
                .header(SET_COOKIE, createRefreshTokenCleaningCookie())
                .build();
    }

    private String extractRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        return cookieUtil.extractRefreshTokenFromCookies(cookies)
                .orElseThrow(() -> new MissingTokenException(refreshTokenConfig));
    }

    private String createRefreshTokenCleaningCookie() {
        return cookieUtil.createRefreshTokenCleaningCookie().toString();
    }

    private String createAccessTokenCleaningCookie() {
        return cookieUtil.createAccessTokenCleaningCookie().toString();
    }

    private String createAccessTokenCookie(String token) {
        return cookieUtil.createAccessTokenCookie(token).toString();
    }

    private String createRefreshTokenCookie(String token) {
        return cookieUtil.createRefreshTokenCookie(token).toString();
    }
}
