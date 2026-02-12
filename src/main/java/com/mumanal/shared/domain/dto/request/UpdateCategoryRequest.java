package com.mumanal.shared.domain.dto.request;


import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
        @NotBlank String name,
        String description
) {} // Nota: No permitimos cambiar el "code" porque rompería el frontend
