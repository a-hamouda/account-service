package com.example.accountservice.application;

import com.example.accountservice.adapter.registration.RegistrationEmailValidation;
import com.example.accountservice.adapter.registration.RegistrationPasswordEncoder;
import com.example.accountservice.adapter.registration.RegistrationPersistence;
import com.example.accountservice.adapter.registration.RegistrationRestPresenter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        RegistrationRestPresenter.class,
        RegistrationPersistence.class,
        RegistrationPasswordEncoder.class,
        RegistrationEmailValidation.class
})
public class AdapterConfiguration {
}
