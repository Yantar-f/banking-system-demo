package com.yantar.bankingsystem.exception;

public class ContactInfoNotProvidedException extends RuntimeException {
    public ContactInfoNotProvidedException() {
        super("Contact info not provided");
    }
}
