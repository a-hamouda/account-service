package com.example.accountservice.adapter.registration;

import com.example.accountservice.core.registration.model.*;
import com.example.accountservice.core.registration.port.RegistrationPersistenceOutputPort;
import com.example.accountservice.infrastructure.persistence.entity.User;
import com.example.accountservice.infrastructure.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationPersistence implements RegistrationPersistenceOutputPort {
    private final UserRepository userRepository;

    @Override
    public Registration save(final Registration registration) {
        final var userBuilder = User.builder()
                                    .username(registration.getEmail().email())
                                    .name(registration.getName().name())
                                    .lastname(registration.getLastname().lastname())
                                    .email(registration.getEmail().email())
                                    .password(registration.getEncodedPassword().password());

        final var savedUser = userRepository.save(userBuilder.build());

        final var savedRegistrationBuilder = Registration.builder()
                                                         .name(new Name(savedUser.getName()))
                                                         .lastname(new Lastname(savedUser.getLastname()))
                                                         .email(new Email(savedUser.getEmail()))
                                                         .encodedPassword(new EncodedPassword(savedUser.getPassword()));

        return savedRegistrationBuilder.build();
    }
}
