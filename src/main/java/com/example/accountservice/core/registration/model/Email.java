package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotBlank;

public record Email(
        @NotBlank(message = "Email cannot be empty")
        @jakarta.validation.constraints.Email(regexp = ".+@.+\\..+", message = "Invalid email address")
        @jakarta.validation.constraints.Email(regexp = ".+@acme.com", message = "Email must end with @acme.com")
        String email) {

    public Email(final String email) {
        this.email = email;

        ModelValidator.validate(this);
    }
}
