package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AssignPermissionToMenuRequest(
        @NotNull(message = "Menu ID is required")
        Integer menuId,

        @NotEmpty(message = "At least one permission ID is required")
        List<Integer> permissionIds
) {}