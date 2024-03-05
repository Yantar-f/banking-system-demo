package com.bankingsystem.exception;

public class EmailOccupiedException extends RuntimeException {
    public EmailOccupiedException(String address) {
        super("Email " + address + " is occupied");
    }
}
