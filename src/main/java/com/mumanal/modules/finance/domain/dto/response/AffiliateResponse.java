package com.mumanal.modules.finance.domain.dto.response;

import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import java.time.LocalDate;

public record AffiliateResponse(
        Integer id,
//        String affiliateCode,
//        LocalDate admissionDate,
//        String status,
        String fullName,
        String firstName,
        String secondName,
        String paternalSurname,
        String maternalSurname,
        String identityCard
) {}