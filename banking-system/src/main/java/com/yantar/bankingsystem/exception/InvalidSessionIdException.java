package com.yantar.bankingsystem.exception;

public class InvalidSessionIdException extends RuntimeException {
    public InvalidSessionIdException(String sessionID) {
        super("Invalid session id: " + sessionID);
    }
}
