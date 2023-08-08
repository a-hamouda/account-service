package com.example.accountservice.core.registration.port;

import com.example.accountservice.core.registration.model.EncodedPassword;
import com.example.accountservice.core.registration.model.PlainPassword;

public interface RegistrationPasswordEncoderOutputPort {
    EncodedPassword encode(final PlainPassword password);
}
