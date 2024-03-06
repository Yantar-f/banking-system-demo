package com.yantar.bankingsystem.model;

public class TokenClaims {
    private Long subjectID;
    private String sessionID;
    private String tokenID;

    public TokenClaims(Long subjectID,
                       String sessionID,
                       String tokenID) {

        this.subjectID = subjectID;
        this.sessionID = sessionID;
        this.tokenID = tokenID;
    }

    public Long getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    @Override
    public int hashCode() {
        return subjectID.hashCode() * sessionID.hashCode() * tokenID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        TokenClaims tokenClaims = (TokenClaims) obj;

        return  subjectID.equals(tokenClaims.subjectID) &&
                sessionID.equals(tokenClaims.sessionID) &&
                tokenID.equals(tokenClaims.tokenID);
    }
}
