package com.example.accountservice.core.registration.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Registration {
    @NotNull(message = "Name is required")
    Name name;
    @NotNull(message = "Last name is required")
    Lastname lastname;
    @NotNull(message = "Email is required")
    Email email;
    @NotNull(message = "Encoded password is required")
    EncodedPassword encodedPassword;

    private Registration(
            final Name name,
            final Lastname lastname,
            final Email email,
            final EncodedPassword encodedPassword) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.encodedPassword = encodedPassword;

        ModelValidator.validate(this);
    }
}
