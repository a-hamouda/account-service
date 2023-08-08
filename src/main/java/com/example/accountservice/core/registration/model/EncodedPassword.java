package com.example.accountservice.core.registration.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Hashing algorithm:   Argon2id
 * Version:             19
 * Hash length:         32
 * Salt length:         16
 * Parallelism:         1
 * Iterations:          2
 * Memory:              19456 (19 MiB)
 *
 * @param password - Plain text password to be encoded.
 */
public record EncodedPassword(
        @NotBlank(message = "Encoded password must be provided")
        @Pattern(regexp = "^\\$argon2id\\$v=19\\$m=19456,t=2,p=1\\$[A-Za-z0-9+/]{22}\\$[A-Za-z0-9+/]{43}$", message =
                "Invalid password hash")
        String password) {
    public EncodedPassword(final String password) {
        this.password = password;

        ModelValidator.validate(this);
    }
}
