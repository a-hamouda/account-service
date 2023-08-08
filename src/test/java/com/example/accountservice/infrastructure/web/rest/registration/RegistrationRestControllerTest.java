package com.example.accountservice.infrastructure.web.rest.registration;

import com.example.accountservice.core.registration.model.*;
import com.example.accountservice.core.registration.port.RegistrationInputPort;
import com.example.accountservice.infrastructure.rest.registration.RegistrationRestController;
import com.example.accountservice.infrastructure.rest.registration.RegistrationRestControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RegistrationRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RegistrationRestController.class})
@DisplayName("Registration rest controller tests")
public class RegistrationRestControllerTest {
    private static final String ENDPOINT_URI = "/api/auth/signup";

    @MockBean
    private RegistrationInputPort registrationPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("""
            Scenario:   Payload has missing fields.
                        
            Given:      Registration endpoint is requested.
            When:       Expected payload fields are missing.
            Then:       A 400 response will be returned.
            And:        Response will have error message of 'invalid-registration-request'
            And:        Registration port will not be called.
            """)
    void throwsConstraintViolationException() throws Exception {
        // arrange
        final var payload = new HashMap<String, Object>();
        final var requestBuilder = post(ENDPOINT_URI)
                .content(objectMapper.writeValueAsString(payload))
                .contentType(MediaType.APPLICATION_JSON);

        // act & assert
        mockMvc.perform(requestBuilder)
               .andDo(handler -> {
                   final var response = handler.getResponse();

                   assertThat(response)
                           .extracting(MockHttpServletResponse::getErrorMessage)
                           .isEqualTo("invalid-registration-request");
               });

        verifyNoInteractions(registrationPort);
    }

    @Test
    @DisplayName("""
            Scenario:   Payload is valid.
                        
            Given:      Registration endpoint is requested.
            When:       Expected payload is valid.
            Then:       Registration port is called.
            """)
    void registrationPortIsCalled() throws Exception {
        // arrange
        final var name = new Name("john");
        final var lastName = new Lastname("doe");
        final var email = new Email("doe@acme.com");
        final var plainPassword = new PlainPassword("secret");

        final var payload = new HashMap<String, Object>();
        payload.put("name", name.name());
        payload.put("lastname", lastName.lastname());
        payload.put("email", email.email());
        payload.put("password", plainPassword.password());

        final var requestBuilder = post(ENDPOINT_URI)
                .content(objectMapper.writeValueAsString(payload))
                .contentType(MediaType.APPLICATION_JSON);

        // act
        mockMvc.perform(requestBuilder).andReturn();

        // assert
        final var registrationPortInteractions = inOrder(registrationPort);
        final var expectedRegistrationRequest = RegistrationRequest.builder()
                                                                   .name(name)
                                                                   .lastname(lastName)
                                                                   .email(email)
                                                                   .plainPassword(plainPassword)
                                                                   .build();
        registrationPortInteractions.verify(registrationPort).register(eq(expectedRegistrationRequest));
        registrationPortInteractions.verifyNoMoreInteractions();
    }
}
