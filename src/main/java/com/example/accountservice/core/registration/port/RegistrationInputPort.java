package com.example.accountservice.core.registration.port;

import com.example.accountservice.core.registration.model.RegistrationRequest;

public interface RegistrationInputPort {
    void register(final RegistrationRequest command);
}
