package com.mumanal.modules.generic.domain.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateCityRequest(
        @Size(max = 100)
        String name,

        @Size(max = 50)
        String country
) {}