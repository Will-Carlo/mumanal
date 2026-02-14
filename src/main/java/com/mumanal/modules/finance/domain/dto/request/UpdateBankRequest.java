package com.mumanal.modules.finance.domain.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateBankRequest(
        @Size(max = 100)
        String name,

        @Size(max = 10)
        String bankCode
) {
}
