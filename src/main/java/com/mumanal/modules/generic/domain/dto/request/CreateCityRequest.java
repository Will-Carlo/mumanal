package com.mumanal.modules.generic.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCityRequest(
        @NotBlank(message = "City name is required")
        @Size(max = 100)
        String name,

        @NotBlank(message = "Country is required")
        @Size(max = 50)
        String country
) {}