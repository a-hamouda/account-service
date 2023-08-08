package com.example.accountservice.core.registration.model;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

abstract class ModelValidator {
    static void validate(Object object) throws ConstraintViolationException {
        try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final var validator = validatorFactory.getValidator();
            final var violations = validator.validate(object);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
    }
}
