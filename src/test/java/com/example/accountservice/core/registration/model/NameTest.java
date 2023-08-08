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


@DisplayName("Name validation test")
public class NameTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("Name cannot be empty")
    void mustNotBeEmpty(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new Name(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Name cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "j0hn", "@doe"})
    @DisplayName("Name can only contain letters")
    void mustOnlyContainLetters(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new Name(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Name can only contain letters");
    }

    @ParameterizedTest
    @ValueSource(strings = {"doe"})
    @DisplayName("Name must be valid")
    void mustBeValid(final String value) {
        assertDoesNotThrow(() -> new Name(value));
    }
}
