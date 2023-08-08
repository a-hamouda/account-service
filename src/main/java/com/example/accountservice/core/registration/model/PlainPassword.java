package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotBlank;

public record PlainPassword(
        @NotBlank(message = "Password cannot be empty")
        String password) {

    public PlainPassword(final String password) {
        this.password = password;

        ModelValidator.validate(this);
    }
}
