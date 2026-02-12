package com.mumanal.shared.domain.dto.request;


import jakarta.validation.constraints.NotBlank;

public record UpdateParameterRequest(
        @NotBlank String name,
        String description,
        Integer sortOrder
        // Nota: El numericCode y categoryId suelen ser inmutables tras crear
) {}