package com.mumanal.modules.finance.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateVoucherRequest(
        @NotNull(message = "Deposit number is required")
        Integer depositNumber,

        @NotNull(message = "Deposit date is required")
        LocalDateTime depositDate,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Period is required")
        LocalDate period,

        @NotNull(message = "Bank information is required")
        @Valid
        BankReferenceDto bank,

        @NotNull(message = "Person information is required")
        @Valid
        PersonReferenceDto affiliate
) {
    public record BankReferenceDto(
            Integer id,
            String name,
            String bankCode
    ) {}

    public record PersonReferenceDto(
            Integer id,
            String firstName,
            String secondName,
            String paternalSurname,
            String maternalSurname,
            String identityCard
    ) {}
}