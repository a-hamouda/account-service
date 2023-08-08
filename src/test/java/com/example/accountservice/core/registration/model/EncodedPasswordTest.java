package com.example.accountservice.core.registration.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Encoded password validation test")
public class EncodedPasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("Blank password hash is invalid")
    void mustNotBeEmpty(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new EncodedPassword(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Encoded password must be provided");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Hashing algorithm:   Bcrypt
            "$bcrypt$v=19$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Version:             18
            "argon2id$v=18$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Memory:              5000
            "argon2id$v=19$m=5000,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Iterations:          10
            "argon2id$v=19$m=19456,t=10,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Parallelism:         3
            "argon2id$v=19$m=19456,t=2,p=3$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Salt length:         15
            "argon2id$v=19$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+9iQ$LY0/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs",
            // Hash length:         33
            "argon2id$v=19$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXisAPioBs",
    })
    @DisplayName("""
            Password hash must satisfy the following requirements:
             * Hashing algorithm:   Argon2id
             * Version:             19
             * Hash length:         32
             * Salt length:         16
             * Parallelism:         1
             * Iterations:          2
             * Memory:              19456 (19 MiB)
            """)
    void mustSatisfyRequirements(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new EncodedPassword(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Invalid password hash");
    }

    @Test
    @DisplayName("Encoded password hashed using Argon2id")
    void mustBeValid() {
        final var value = "$argon2id$v=19$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc" +
                "+UlAlXiAPioBs";
        assertDoesNotThrow(() -> new EncodedPassword(value));
    }
}
