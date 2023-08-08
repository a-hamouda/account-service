package com.example.accountservice.adapter.registration;

import com.example.accountservice.core.registration.model.EncodedPassword;
import com.example.accountservice.core.registration.model.PlainPassword;
import com.example.accountservice.core.registration.port.RegistrationPasswordEncoderOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationPasswordEncoder implements RegistrationPasswordEncoderOutputPort {
    private final PasswordEncoder passwordEncoder;

    @Override
    public EncodedPassword encode(PlainPassword password) {
        final var rawEncodedPassword = passwordEncoder.encode(password.password());
        return new EncodedPassword(rawEncodedPassword);
    }
}
