package com.example.accountservice.core.registration.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Registration request validation test")
public class RegistrationRequestTest {
    @Test
    void nameMustNotBeNull() {
        final var registrationRequestBuilder = RegistrationRequest
                .builder()
                .lastname(lastname())
                .email(email())
                .plainPassword(plainPassword());
        final var exception = assertThrows(ConstraintViolationException.class, registrationRequestBuilder::build);
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Name is required");
    }

    @Test
    void lastnameMustNotBeNull() {
        final var registrationRequestBuilder = RegistrationRequest
                .builder()
                .name(name())
                .email(email())
                .plainPassword(plainPassword());
        final var exception = assertThrows(ConstraintViolationException.class, registrationRequestBuilder::build);
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Last name is required");
    }

    @Test
    void emailMustNotBeNull() {
        final var registrationRequestBuilder = RegistrationRequest
                .builder()
                .name(name())
                .lastname(lastname())
                .plainPassword(plainPassword());
        final var exception = assertThrows(ConstraintViolationException.class, registrationRequestBuilder::build);
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Email is required");
    }

    @Test
    void passwordMustNotBeNull() {
        final var registrationRequestBuilder = RegistrationRequest
                .builder()
                .name(name())
                .lastname(lastname())
                .email(email());
        final var exception = assertThrows(ConstraintViolationException.class, registrationRequestBuilder::build);
        final var violations = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage);
        assertThat(violations).contains("Password is required");
    }


    private Name name() {
        return new Name("john");
    }

    private Lastname lastname() {
        return new Lastname("doe");
    }

    private Email email() {
        return new Email("john@acme.com");
    }

    private PlainPassword plainPassword() {
        return new PlainPassword("secret");
    }
}
