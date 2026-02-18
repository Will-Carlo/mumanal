package com.mumanal.modules.generic.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePersonRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String secondName,

        @Size(max = 45)
        String paternalSurname,

        @Size(max = 45)
        String maternalSurname,

        @Size(max = 45)
        String identityCard,

        // @NotNull(message = "Phone number is required")
        Integer phoneNumber,

        @Size(max = 100)
        @Email(message = "Invalid email format")
        String email,

        @Size(max = 100)
        String imageUrl
) {}