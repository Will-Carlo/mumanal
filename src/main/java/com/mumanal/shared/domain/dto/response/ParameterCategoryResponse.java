package com.mumanal.shared.domain.dto.response;

public record ParameterCategoryResponse(
        Integer id,
        String code,
        String name,
        String description
) {}