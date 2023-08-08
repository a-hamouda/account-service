package com.example.accountservice.infrastructure.web.rest.registration;

import com.example.accountservice.adapter.registration.RegistrationRestPresenter;
import com.example.accountservice.core.registration.model.Email;
import com.example.accountservice.core.registration.model.Lastname;
import com.example.accountservice.core.registration.model.Name;
import com.example.accountservice.core.registration.port.RegistrationPresenterOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MimeTypeUtils;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RegistrationRestPresenter.class, ObjectMapper.class})
@WebAppConfiguration
class RegistrationRestPresenterTest {

    @Autowired
    private RegistrationPresenterOutputPort presenter;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockHttpServletResponse response;

    @Test
    void presentSuccessfulRegistration() throws UnsupportedEncodingException, JsonProcessingException {
        // arrange
        final var name = new Name("john");
        final var lastname = new Lastname("doe");
        final var email = new Email("john@acme.com");

        // act
        presenter.presentSuccessfulRegistration(name, lastname, email);

        // assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MimeTypeUtils.APPLICATION_JSON_VALUE);
        final var actualBody = objectMapper.readValue(response.getContentAsString(), Object.class);
        assertThat(actualBody).hasFieldOrPropertyWithValue("name", name.name())
                              .hasFieldOrPropertyWithValue("lastname", lastname.lastname())
                              .hasFieldOrPropertyWithValue("email", email.email());
    }

    @Test
    void presentEmailAlreadyRegisteredError() throws UnsupportedEncodingException, JsonProcessingException {
        // arrange
        final var email = new Email("john@acme.com");

        // act
        presenter.presentEmailAlreadyRegisteredError(email);

        // assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentType()).isEqualTo(MimeTypeUtils.APPLICATION_JSON_VALUE);
        final var actualBody = objectMapper.readValue(response.getContentAsString(), Object.class);
        assertThat(actualBody).hasFieldOrPropertyWithValue("message",
                "Email address '%s' is already registered".formatted(email.email()));
    }
}
