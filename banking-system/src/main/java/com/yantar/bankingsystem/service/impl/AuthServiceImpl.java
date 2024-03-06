package com.yantar.bankingsystem.service.impl;

import com.yantar.bankingsystem.entity.RoleEntity;
import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.entity.UserEntity;
import com.yantar.bankingsystem.exception.InvalidAccountDataException;
import com.yantar.bankingsystem.exception.InvalidSessionIdException;
import com.yantar.bankingsystem.exception.InvalidTokenException;
import com.yantar.bankingsystem.exception.UserNotFoundException;
import com.yantar.bankingsystem.model.SessionId;
import com.yantar.bankingsystem.model.TokenClaims;
import com.yantar.bankingsystem.model.UserLoginCredentials;
import com.yantar.bankingsystem.repository.SessionRepository;
import com.yantar.bankingsystem.repository.UserRepository;
import com.yantar.bankingsystem.service.AuthService;
import com.yantar.bankingsystem.util.SessionIdShaper;
import com.yantar.bankingsystem.util.TokenIdGenerator;
import com.yantar.bankingsystem.util.TokenUtil;
import com.yantar.bankingsystem.util.UserSpecBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserSpecBuilder userSpecBuilder;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionIdShaper sessionIdShaper;
    private final TokenIdGenerator tokenIdGenerator;
    private final TokenUtil tokenUtil;
    private final Clock clock;

    public AuthServiceImpl(UserRepository userRepository,
                           UserSpecBuilder userSpecBuilder,
                           SessionRepository sessionRepository,
                           PasswordEncoder passwordEncoder,
                           SessionIdShaper sessionIdShaper,
                           TokenIdGenerator tokenIdGenerator,
                           TokenUtil tokenUtil,
                           Clock clock) {
        this.userRepository = userRepository;
        this.userSpecBuilder = userSpecBuilder;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionIdShaper = sessionIdShaper;
        this.tokenIdGenerator = tokenIdGenerator;
        this.tokenUtil = tokenUtil;
        this.clock = clock;
    }

    @Override
    public SessionEntity createSession(UserLoginCredentials loginCredentials) {
        UserEntity user = findUserByIdentifier(loginCredentials.userIdentifier());

        validatePasswords(loginCredentials.password(), user.getEncodedPassword());

        return generateSessionFor(user);
    }

    private SessionEntity generateSessionFor(UserEntity user) {
        SessionId sessionId = generateSessionID(user.getId());
        String tokenId = generateTokenID();
        String convertedSessionId = convertSessionID(sessionId);

        TokenClaims tokenClaims = new TokenClaims(user.getId(), convertedSessionId, tokenId);

        String accessToken = generateAccessTokenFrom(tokenClaims);
        String refreshToken = generateRefreshTokenFrom(tokenClaims);
        Instant createdAt = stampTime();

        return sessionRepository.insert(new SessionEntity(
                user.getId(),
                sessionId.getSessionKey(),
                createdAt,
                user.getRoles().stream().map(RoleEntity::getDesignation).collect(Collectors.toSet()),
                accessToken,
                refreshToken,
                tokenId
        ));
    }

    @Override
    public SessionEntity refreshSession(String refreshToken) {
        try {
            TokenClaims claims = parseRefreshToken(refreshToken);
            SessionId sessionId = parseSessionID(claims.getSessionId());

            SessionEntity session = sessionRepository
                    .findById(sessionId)
                    .orElseThrow(() -> new InvalidTokenException(refreshToken));

            if ( ! session.getTokenId().equals(claims.getTokenId()))
                throw new InvalidTokenException(refreshToken);

            claims.setTokenId(generateTokenID());

            session.setAccessToken(generateAccessTokenFrom(claims));
            session.setRefreshToken(generateRefreshTokenFrom(claims));
            session.setTokenId(claims.getTokenId());
            session.setRefreshedAt(stampTime());

            return sessionRepository.insert(session);
        } catch (InvalidSessionIdException exception) {
            throw new InvalidTokenException(refreshToken);
        }
    }

    @Override
    public void deleteSession(String refreshToken) {
        try {
            TokenClaims claims = parseRefreshToken(refreshToken);
            SessionId sessionId = parseSessionID(claims.getSessionId());

            SessionEntity session = sessionRepository
                    .findById(sessionId)
                    .orElseThrow(() -> new InvalidTokenException(refreshToken));

            if (! session.getTokenId().equals(claims.getTokenId()))
                throw new InvalidTokenException(refreshToken);

            sessionRepository.deleteById(sessionId);
        } catch (InvalidSessionIdException exception) {
            throw new InvalidTokenException(refreshToken);
        }
    }

    private UserEntity findUserByIdentifier(String identifier) {
        Specification<UserEntity> specification = userSpecBuilder.specFromIdentifier(identifier);

        return userRepository
                .findOne(specification)
                .orElseThrow(() -> new UserNotFoundException(identifier));
    }

    private void validatePasswords(String rawPassword, String encodedPassword) {
        if (! passwordEncoder.matches(rawPassword, encodedPassword))
            throw new InvalidAccountDataException();
    }

    private Instant stampTime() {
        return Instant.now(clock);
    }

    private String generateRefreshTokenFrom(TokenClaims claims) {
        return tokenUtil.generateRefreshToken(claims);
    }

    private String generateAccessTokenFrom(TokenClaims claims) {
        return tokenUtil.generateAccessToken(claims);
    }

    private String convertSessionID(SessionId sessionId) {
        return sessionIdShaper.convert(sessionId);
    }

    private String generateTokenID() {
        return tokenIdGenerator.generate();
    }

    private SessionId generateSessionID(Long userId) {
        return sessionIdShaper.generate(userId);
    }

    private SessionId parseSessionID(String sessionID) {
        return sessionIdShaper.parse(sessionID);
    }

    private TokenClaims parseRefreshToken(String refreshToken) {
        return tokenUtil.parseRefreshToken(refreshToken);
    }
}
