package com.mumanal.modules.finance.domain.dto.request;

import com.mumanal.modules.generic.domain.dto.request.CreatePersonRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateAffiliateRequest(
//        @NotBlank(message = "Affiliate code is required")
//        @Size(max = 20)
//        String affiliateCode,

//        @NotNull(message = "Admission date is required")
//        LocalDate admissionDate,

//        @NotNull(message = "Status is required")
//        String status, // ACTIVE, PASSIVE, etc.

        @NotNull(message = "Person data is required")
        @Valid
        CreatePersonRequest person
) {}