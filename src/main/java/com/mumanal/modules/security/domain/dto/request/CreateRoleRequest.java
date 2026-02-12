package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateRoleRequest(
        @NotBlank(message = "Name is required")
        String name, // Ej: "SALES_MANAGER"

        @NotBlank(message = "Description is required")
        String description,

        List<Integer> permissionIds
) {}