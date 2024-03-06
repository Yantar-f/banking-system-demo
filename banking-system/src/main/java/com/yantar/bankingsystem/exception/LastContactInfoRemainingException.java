package com.yantar.bankingsystem.exception;

public class LastContactInfoRemainingException extends RuntimeException {
    public LastContactInfoRemainingException() {
        super("There is last contact info");
    }
}
