package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegistrationRequest {
    @NotNull(message = "Name is required")
    Name name;
    @NotNull(message = "Last name is required")
    Lastname lastname;
    @NotNull(message = "Email is required")
    Email email;
    @NotNull(message = "Password is required")
    PlainPassword plainPassword;

    private RegistrationRequest(
            final Name name,
            final Lastname lastname,
            final Email email,
            final PlainPassword plainPassword) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.plainPassword = plainPassword;

        ModelValidator.validate(this);
    }
}
