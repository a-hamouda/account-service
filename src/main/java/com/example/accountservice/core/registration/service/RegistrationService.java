package com.example.accountservice.core.registration.service;

import com.example.accountservice.core.registration.model.Registration;
import com.example.accountservice.core.registration.model.RegistrationRequest;
import com.example.accountservice.core.registration.port.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationService implements RegistrationInputPort {
    private final RegistrationEmailValidationOutputPort emailValidation;
    private final RegistrationPasswordEncoderOutputPort passwordEncoder;
    private final RegistrationPersistenceOutputPort persistence;
    private final RegistrationPresenterOutputPort presenter;

    @Override
    public void register(final RegistrationRequest request) {
        final var isEmailRegistered = emailValidation.isAlreadyRegistered(request.getEmail());
        if (isEmailRegistered) {
            presenter.presentEmailAlreadyRegisteredError(request.getEmail());
            return;
        }
        final var encodedPassword = passwordEncoder.encode(request.getPlainPassword());
        final var registration = Registration.builder()
                                             .name(request.getName())
                                             .lastname(request.getLastname())
                                             .email(request.getEmail())
                                             .encodedPassword(encodedPassword).build();
        final var savedRegistration = persistence.save(registration);
        presenter.presentSuccessfulRegistration(
                savedRegistration.getName(),
                savedRegistration.getLastname(),
                savedRegistration.getEmail()
        );
    }
}
