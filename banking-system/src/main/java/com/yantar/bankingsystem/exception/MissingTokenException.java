package com.yantar.bankingsystem.exception;

import com.yantar.bankingsystem.config.TokenConfig;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException(TokenConfig config) {
        super("Missing " + config.getTokenTypeName());
    }
}
