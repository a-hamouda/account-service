package com.example.accountservice.core.registration.service;

import com.example.accountservice.core.registration.model.*;
import com.example.accountservice.core.registration.port.RegistrationEmailValidationOutputPort;
import com.example.accountservice.core.registration.port.RegistrationPasswordEncoderOutputPort;
import com.example.accountservice.core.registration.port.RegistrationPersistenceOutputPort;
import com.example.accountservice.core.registration.port.RegistrationPresenterOutputPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@DisplayName("Registration service tests")
public class RegistrationServiceTest {
    private final RegistrationEmailValidationOutputPort emailValidationPort =
            mock(RegistrationEmailValidationOutputPort.class);

    private final RegistrationPasswordEncoderOutputPort passwordEncoderPort =
            mock(RegistrationPasswordEncoderOutputPort.class);

    private final RegistrationPersistenceOutputPort persistencePort =
            mock(RegistrationPersistenceOutputPort.class);

    private final RegistrationPresenterOutputPort presenterPort =
            mock(RegistrationPresenterOutputPort.class);

    @Test
    @DisplayName("""
            Scenario:   Successful registration.
                        
            Given:      A registration request.
            When:       Email is not registered.
            Then:       Password should be encoded.
            And:        Registration should be persisted.
            And:        Successful registration should be presented.
            """)
    void successfulRegistration() {
        // arrange
        final var email = email();
        final var registrationRequest = registrationRequest(email);
        final var encodedPassword = new EncodedPassword("$argon2id$v=19$m=19456,t=2,p=1$ImdtwV48OPGx4I1Em+h9iQ$LY0H/VdLnl2LpcwI8SBGQGjEYlTkvc+UlAlXiAPioBs");
        final var registration = Registration.builder()
                                             .name(registrationRequest.getName())
                                             .lastname(registrationRequest.getLastname())
                                             .email(registrationRequest.getEmail())
                                             .encodedPassword(encodedPassword)
                                             .build();

        // stub
        when(emailValidationPort.isAlreadyRegistered(email)).thenReturn(false);
        when(passwordEncoderPort.encode(registrationRequest.getPlainPassword())).thenReturn(encodedPassword);
        when(persistencePort.save(eq(registration))).thenReturn(registration);

        // act
        final var service = new RegistrationService(
                emailValidationPort,
                passwordEncoderPort,
                persistencePort,
                presenterPort
        );
        service.register(registrationRequest);

        // assert
        final var emailValidationInteractions = inOrder(emailValidationPort);
        emailValidationInteractions.verify(emailValidationPort).isAlreadyRegistered(email);
        emailValidationInteractions.verifyNoMoreInteractions();

        final var passwordEncoderInteractions = inOrder(passwordEncoderPort);
        passwordEncoderInteractions.verify(passwordEncoderPort).encode(registrationRequest.getPlainPassword());
        passwordEncoderInteractions.verifyNoMoreInteractions();

        final var persistencePortInteractions = inOrder(persistencePort);
        persistencePortInteractions.verify(persistencePort).save(registration);

        final var presenterInteractions = inOrder(presenterPort);
        presenterInteractions.verify(presenterPort).presentSuccessfulRegistration(registration.getName(),
                registration.getLastname(), registration.getEmail());
        presenterInteractions.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("""
            Scenario:   Email already registered.
                        
            Given:      A registration request.
            When:       Email is already registered.
            Then:       Email already registered error should be presented.
            And:        Registration should not be persisted.
            """)
    void emailAlreadyRegistered() {
        // arrange
        final var email = email();
        final var registrationRequest = registrationRequest(email);

        // stub
        when(emailValidationPort.isAlreadyRegistered(email)).thenReturn(true);

        // act
        final var service = new RegistrationService(
                emailValidationPort,
                passwordEncoderPort,
                persistencePort,
                presenterPort
        );
        service.register(registrationRequest);

        // assert
        final var emailValidationInteractions = inOrder(emailValidationPort);
        emailValidationInteractions.verify(emailValidationPort).isAlreadyRegistered(email);
        emailValidationInteractions.verifyNoMoreInteractions();

        final var presenterInteractions = inOrder(presenterPort);
        presenterInteractions.verify(presenterPort).presentEmailAlreadyRegisteredError(email);
        presenterInteractions.verifyNoMoreInteractions();

        final var persistencePortInteractions = inOrder(persistencePort);
        persistencePortInteractions.verifyNoMoreInteractions();
    }

    private Email email() {
        return new Email("john@acme.com");
    }

    private RegistrationRequest registrationRequest(final Email email) {
        final var name = new Name("Doe");
        final var lastName = new Lastname("John");
        final var password = new PlainPassword("secret");
        return RegistrationRequest.builder()
                                  .name(name)
                                  .lastname(lastName)
                                  .email(email)
                                  .plainPassword(password)
                                  .build();
    }
}
