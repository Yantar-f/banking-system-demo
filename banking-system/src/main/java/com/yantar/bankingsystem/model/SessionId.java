package com.yantar.bankingsystem.model;

public class SessionId {
    private Long userId;
    private String sessionKey;

    public SessionId(Long userId, String sessionKey) {
        this.userId = userId;
        this.sessionKey = sessionKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public int hashCode() {
        return userId.hashCode() * sessionKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        SessionId sessionID = (SessionId) obj;

        return userId.equals(sessionID.userId) && sessionKey.equals(sessionID.sessionKey);
    }
}
