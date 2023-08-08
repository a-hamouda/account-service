package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Lastname(
        @NotBlank(message = "Last name cannot be empty")
        @Pattern(regexp = "[a-zA-Z]+", message = "Last name can only contain letters")
        String lastname) {

    public Lastname(final String lastname) {
        this.lastname = lastname;

        ModelValidator.validate(this);
    }
}
