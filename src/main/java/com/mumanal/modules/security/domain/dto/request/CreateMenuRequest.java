package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateMenuRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Route is required")
        String route,

        String icon,

        Integer parentMenuId
) {}