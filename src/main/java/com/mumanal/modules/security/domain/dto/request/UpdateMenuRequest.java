package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateMenuRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Route is required")
        String route,

        String icon,

        @NotNull(message = "Sort order is required")
        @PositiveOrZero
        Integer sortOrder,

        @NotNull(message = "Is Public flag is required")
        Boolean isPublic,

        @NotNull(message = "Status is required")
        Boolean status,

        Integer parentMenuId // Puede cambiar de padre
) {}