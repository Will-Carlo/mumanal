package com.mumanal.modules.finance.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBankRequest(
        @NotBlank(message = "Bank name is required")
        @Size(max = 100, message = "Bank name must be less than 100 characters")
        String name,

        @NotBlank(message = "Bank code is required")
        @Size(max = 20, message = "Bank code must be less than 20 characters")
        String bankCode
) {}