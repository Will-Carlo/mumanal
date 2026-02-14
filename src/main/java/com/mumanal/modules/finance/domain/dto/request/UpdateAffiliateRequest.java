package com.mumanal.modules.finance.domain.dto.request;

import com.mumanal.modules.generic.domain.dto.request.UpdatePersonRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;

public record UpdateAffiliateRequest(
//        String affiliateCode,
//        LocalDate admissionDate,
//        String status,

        @Valid
        UpdatePersonRequest person
) {}