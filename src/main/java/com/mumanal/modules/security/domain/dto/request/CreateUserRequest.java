package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateUserRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Person ID is required")
        Integer personId,

        @NotEmpty(message = "At least one role is required")
        List<Integer> roleIds // Ej: [2, 3] (ADMIN, SALES)
) {}