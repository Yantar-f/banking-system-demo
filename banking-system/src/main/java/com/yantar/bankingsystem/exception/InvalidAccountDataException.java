package com.yantar.bankingsystem.exception;

public class InvalidAccountDataException extends RuntimeException {
    public InvalidAccountDataException() {
        super("Invalid account data");
    }
}
