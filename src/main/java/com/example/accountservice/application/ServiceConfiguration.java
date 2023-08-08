package com.example.accountservice.application;

import com.example.accountservice.core.registration.port.*;
import com.example.accountservice.core.registration.service.RegistrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public RegistrationInputPort registrationInputPort(
            RegistrationEmailValidationOutputPort emailValidation,
            RegistrationPasswordEncoderOutputPort passwordEncoder,
            RegistrationPersistenceOutputPort persistence,
            RegistrationPresenterOutputPort presenter) {
        return new RegistrationService(emailValidation, passwordEncoder, persistence, presenter);
    }
}
