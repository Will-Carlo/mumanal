package com.mumanal.modules.generic.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdatePersonRequest(
        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String secondName,

        @Size(max = 45)
        String paternalSurname,

        @Size(max = 45)
        String maternalSurname,

        Integer phoneNumber,

        @Size(max = 100)
        @Email
        String email,

        @Size(max = 100)
        String imageUrl
) {}