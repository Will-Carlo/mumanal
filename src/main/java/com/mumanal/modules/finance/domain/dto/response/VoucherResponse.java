package com.mumanal.modules.finance.domain.dto.response;

import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record VoucherResponse(
        Integer id,
        Integer depositNumber,
        LocalDateTime depositDate,
        LocalDateTime registrationDate,
        BigDecimal amount,
        LocalDate period,
        BankResponse bank,
        PersonResponse person
) {}