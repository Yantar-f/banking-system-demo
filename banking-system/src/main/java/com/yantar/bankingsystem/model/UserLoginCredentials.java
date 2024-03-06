package com.yantar.bankingsystem.model;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginCredentials(
        @NotEmpty
        String userIdentifier,

        @NotEmpty
        String password
) {
}
