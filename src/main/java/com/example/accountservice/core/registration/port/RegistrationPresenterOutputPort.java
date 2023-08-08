package com.example.accountservice.core.registration.port;

import com.example.accountservice.core.registration.model.Email;
import com.example.accountservice.core.registration.model.Lastname;
import com.example.accountservice.core.registration.model.Name;
import com.example.accountservice.core.registration.model.Registration;

public interface RegistrationPresenterOutputPort {
    void presentSuccessfulRegistration(final Name name, final Lastname lastname, final Email email);

    void presentEmailAlreadyRegisteredError(final Email email);
}
