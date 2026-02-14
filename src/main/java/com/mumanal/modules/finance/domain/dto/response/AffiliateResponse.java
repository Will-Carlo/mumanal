package com.mumanal.modules.finance.domain.dto.response;

import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import java.time.LocalDate;

public record AffiliateResponse(
        Integer id,
//        String affiliateCode,
//        LocalDate admissionDate,
//        String status,
        String firstName,
        String secondName,
        String paternalSurname,
        String maternalSurname,
        String fullName,
        String identityCard
) {}