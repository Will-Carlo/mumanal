package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreatePermissionRequest(
        @NotBlank(message = "Code is required")
        @Pattern(regexp = "^[A-Z]+_[A-Z]+_[A-Z]+$",
                message = "The code must follow the format MODULE_RESOURCE_ACTION (ej: SEC_USER_READ)")
        String code,

        @NotBlank(message = "Name is required")
        String name,

        String description
) {}