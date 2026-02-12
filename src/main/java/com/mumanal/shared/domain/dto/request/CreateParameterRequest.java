package com.mumanal.shared.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateParameterRequest(
        @NotNull(message = "Category ID is required")
        Integer categoryId,

        @NotNull(message = "Numeric Code is required")
        Integer numericCode, // Ej: 901

        @NotBlank(message = "Name is required")
        String name, // Ej: "Bitcoin"

        String description,
        Integer sortOrder
) {}