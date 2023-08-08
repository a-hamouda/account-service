package com.example.accountservice.core.registration.port;

import com.example.accountservice.core.registration.model.Email;

public interface RegistrationEmailValidationOutputPort {
    boolean isAlreadyRegistered(final Email email);
}
