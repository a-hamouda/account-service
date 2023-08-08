package com.example.accountservice.infrastructure.rest.registration;

import com.example.accountservice.core.registration.model.*;
import com.example.accountservice.core.registration.port.RegistrationInputPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationRestController {
    private final RegistrationInputPort registrationPort;

    @PostMapping("/api/auth/signup")
    public void register(@RequestBody final RegistrationRequestBody body) {
        final var request = RegistrationRequest.builder()
                                               .name(new Name(body.name()))
                                               .lastname(new Lastname(body.lastname()))
                                               .email(new Email(body.email()))
                                               .plainPassword(new PlainPassword(body.password()))
                                               .build();
        registrationPort.register(request);
    }
}
