package com.bankingsystem.exception;

public class PhoneNumberOccupiedException extends RuntimeException {
    public PhoneNumberOccupiedException(String number) {
        super("Phone " + number + " is occupied");
    }
}
