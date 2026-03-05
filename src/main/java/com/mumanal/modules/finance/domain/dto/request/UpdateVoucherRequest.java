package com.mumanal.modules.finance.domain.dto.request;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateVoucherRequest(
        String depositNumber,
        LocalDateTime depositDate,
        @Positive
        BigDecimal amount,
        LocalDate period,
        Integer bankId,
        Integer personId
) {}