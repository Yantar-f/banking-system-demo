package com.yantar.bankingsystem.model;

public class TokenClaims {
    private Long subjectId;
    private String sessionId;
    private String tokenId;

    public TokenClaims(Long subjectId,
                       String sessionId,
                       String tokenId) {

        this.subjectId = subjectId;
        this.sessionId = sessionId;
        this.tokenId = tokenId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public int hashCode() {
        return subjectId.hashCode() * sessionId.hashCode() * tokenId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        TokenClaims tokenClaims = (TokenClaims) obj;

        return  subjectId.equals(tokenClaims.subjectId) &&
                sessionId.equals(tokenClaims.sessionId) &&
                tokenId.equals(tokenClaims.tokenId);
    }
}
