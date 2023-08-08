package com.example.accountservice.infrastructure.rest.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RegistrationRequestBody(
        @JsonProperty("name")
        String name,
        @JsonProperty("lastname")
        String lastname,
        @JsonProperty("email")
        String email,
        @JsonProperty("password")
        String password) {
}
