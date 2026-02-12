package com.mumanal.shared.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// --- CATEGORÍAS (MAESTRO) ---

public record CreateCategoryRequest(
        @NotBlank(message = "Code is required")
        @Size(max = 50)
        String code, // Ej: "NEW_PAYMENT_TYPE"

        @NotBlank(message = "Name is required")
        @Size(max = 100)
        String name,

        String description
) {}