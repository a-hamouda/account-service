package com.example.accountservice.core.registration.port;

import com.example.accountservice.core.registration.model.Registration;

public interface RegistrationPersistenceOutputPort {
    Registration save(final Registration registration);
}
