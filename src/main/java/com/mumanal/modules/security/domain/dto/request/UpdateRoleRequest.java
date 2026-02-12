package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateRoleRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        List<Integer> permissionIds // La nueva lista de permisos (reemplaza la anterior)
) {}