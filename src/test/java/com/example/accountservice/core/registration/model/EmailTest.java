package com.example.accountservice.core.registration.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Email validation test")
public class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("Email cannot be empty")
    void mustNotBeEmpty(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new Email(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Email cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"this is an email", "@gmail.com", "@acme.com"})
    @DisplayName("Email cannot have incorrect format")
    void mustHaveValidEmailFormat(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new Email(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Invalid email address");
    }

    @ParameterizedTest
    @ValueSource(strings = {"one@example.com", "two@another.org"})
    @DisplayName("Email must have @acme.com")
    void mustHaveACMEDomain(final String value) {
        final var exception = assertThrows(ConstraintViolationException.class, () -> new Email(value));
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Email must end with @acme.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {"one@acme.com", "two.3@acme.com"})
    @DisplayName("Email must have @acme.com")
    void mustBeValid(final String value) {
        assertDoesNotThrow(() -> new Email(value));
    }
}
