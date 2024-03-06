package com.yantar.bankingsystem.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String token) {
        super("invalid token: " + token);
    }
}
