package com.mumanal.modules.generic.domain.dto.response;

public record PersonResponse(
        Integer id,
        String firstName,
        String secondName,
        String paternalSurname,
        String maternalSurname,
        String fullName,
        String identityCard
) {}