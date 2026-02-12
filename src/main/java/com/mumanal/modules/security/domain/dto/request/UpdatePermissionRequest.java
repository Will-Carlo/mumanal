package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePermissionRequest(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Status is required")
        Boolean status // To logically activate/deactivate, apart from deletion
) {}