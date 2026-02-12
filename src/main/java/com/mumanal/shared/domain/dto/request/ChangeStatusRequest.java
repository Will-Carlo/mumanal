package com.mumanal.shared.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(
        @NotNull(message = "Status is required")
        Boolean status
) {}