package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAssignmentRequest(
        @NotNull(message = "User ID is required")
        Integer userId,

        @NotNull(message = "Role ID is required")
        Integer roleId,

        LocalDateTime expirationDate
) {}