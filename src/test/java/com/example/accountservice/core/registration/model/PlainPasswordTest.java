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

@DisplayName("Plain password validation test")
public class PlainPasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("Password cannot be empty")
    void mustNotBeEmpty(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new PlainPassword(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Password cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"secret"})
    @DisplayName("Password must be valid")
    void mustBeValid(final String value) {
        assertDoesNotThrow(() -> new PlainPassword(value));
    }
}
