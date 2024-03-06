package com.yantar.bankingsystem.util.impl;

import com.yantar.bankingsystem.exception.InvalidSessionIdException;
import com.yantar.bankingsystem.model.SessionId;
import com.yantar.bankingsystem.util.SessionIdShaper;
import com.yantar.bankingsystem.util.SessionKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class SessionIdShaperImpl implements SessionIdShaper {
    private final char separator = '.';
    private final long minSessionIdLength;
    private final SessionKeyGenerator sessionKeyGenerator;

    public SessionIdShaperImpl(SessionKeyGenerator sessionKeyGenerator) {
        this.sessionKeyGenerator = sessionKeyGenerator;
        minSessionIdLength = sessionKeyGenerator.getKeyLength() + 2;
    }

    @Override
    public SessionId generate(Long userId) {
        String sessionKey = generateSessionKey();
        return new SessionId(userId, sessionKey);
    }

    private String generateSessionKey() {
        return sessionKeyGenerator.generate();
    }

    @Override
    public SessionId parse(String sessionId) {
        int separatorIndex = computeSeparatorIndex(sessionId);
        Long userId = extractUserId(sessionId, separatorIndex);
        String sessionKey = extractSessionKey(sessionId, separatorIndex);

        return new SessionId(userId, sessionKey);
    }

    private int computeSeparatorIndex(String sessionID) {
        if (sessionID.length() < minSessionIdLength)
            throw new InvalidSessionIdException(sessionID);

        int separatorIndex = Math.toIntExact(sessionID.length() - 1 - sessionKeyGenerator.getKeyLength());

        if (sessionID.charAt(separatorIndex) != separator)
            throw new InvalidSessionIdException(sessionID);

        return separatorIndex;
    }

    private String extractSessionKey(String sessionID, int separatorIndex) {
        return sessionID.substring(separatorIndex + 1);
    }

    private Long extractUserId(String sessionID, int separatorIndex) {
        try {
            return Long.valueOf(sessionID.substring(0, separatorIndex));
        } catch (ClassCastException exception) {
            throw new InvalidSessionIdException(sessionID);
        }
    }

    @Override
    public String convert(SessionId sessionId) {
        return sessionId.getUserId() + separator + sessionId.getSessionKey();
    }
}
