package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Name(
        @NotBlank(message = "Name cannot be empty")
        @Pattern(regexp = "[a-zA-Z]+", message = "Name can only contain letters")
        String name) {

    public Name(final String name) {
        this.name = name;

        ModelValidator.validate(this);
    }
}
