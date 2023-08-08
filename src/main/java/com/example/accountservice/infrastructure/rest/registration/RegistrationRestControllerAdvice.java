package com.example.accountservice.infrastructure.rest.registration;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = RegistrationRestController.class)
@AllArgsConstructor
public class RegistrationRestControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException exception)  {
        final var body = new HashMap<String, Object>();
        final var errors = exception.getConstraintViolations().stream().map(violation ->
                Map.of(violation.getPropertyPath().toString(), violation.getMessage())
        ).toList();
        body.put("error", "registration-2");
        body.put("message", "Invalid request body");
        body.put("details", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
