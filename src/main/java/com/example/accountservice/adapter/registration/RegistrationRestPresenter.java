package com.example.accountservice.adapter.registration;

import com.example.accountservice.core.registration.model.Email;
import com.example.accountservice.core.registration.model.Lastname;
import com.example.accountservice.core.registration.model.Name;
import com.example.accountservice.core.registration.port.RegistrationPresenterOutputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequestScope
@AllArgsConstructor
public class RegistrationRestPresenter implements RegistrationPresenterOutputPort {
    private static final String contentType = "application/json";
    private final HttpServletResponse response;
    private final ObjectMapper objectMapper;

    @Override
    public void presentSuccessfulRegistration(final Name name, final Lastname lastname, final Email email) {
        final var body = new HashMap<String, Object>();
        body.put("name", name.name());
        body.put("lastname", lastname.lastname());
        body.put("email", email.email());

        try (final var writer = response.getWriter()) {
            response.setContentType(contentType);
            response.setStatus(200);
            writer.write(objectMapper.writeValueAsString(body));
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void presentEmailAlreadyRegisteredError(final Email email) {
        final var body = new HashMap<String, Object>();
        body.put("error", "registration-1");
        body.put("message", "Email address '%s' is already registered".formatted(email.email()));

        try (final var writer = response.getWriter()) {
            response.setContentType(contentType);
            response.setStatus(400);
            writer.write(objectMapper.writeValueAsString(body));
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
