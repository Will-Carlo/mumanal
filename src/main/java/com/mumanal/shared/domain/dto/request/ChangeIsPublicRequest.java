package com.mumanal.shared.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeIsPublicRequest(
        @NotNull(message = "IsPublic value is required")
        Boolean isPublic
) {}