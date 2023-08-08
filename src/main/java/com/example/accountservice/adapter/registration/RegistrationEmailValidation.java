package com.example.accountservice.adapter.registration;

import com.example.accountservice.core.registration.model.Email;
import com.example.accountservice.core.registration.port.RegistrationEmailValidationOutputPort;
import com.example.accountservice.infrastructure.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationEmailValidation implements RegistrationEmailValidationOutputPort {
    private final UserRepository userRepository;

    @Override
    public boolean isAlreadyRegistered(final Email email) {
        return userRepository.existsByEmailIgnoreCase(email.email());
    }
}
