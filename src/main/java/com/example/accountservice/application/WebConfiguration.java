package com.example.accountservice.application;

import com.example.accountservice.infrastructure.rest.registration.RegistrationRestController;
import com.example.accountservice.infrastructure.rest.registration.RegistrationRestControllerAdvice;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        RegistrationRestController.class,
        RegistrationRestControllerAdvice.class
})
public class WebConfiguration {
}
