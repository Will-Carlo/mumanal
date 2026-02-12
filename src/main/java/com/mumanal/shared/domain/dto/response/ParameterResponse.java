package com.mumanal.shared.domain.dto.response;

public record ParameterResponse(
        Integer id,
        Integer numericCode,
        String name,
        String description,
        Integer sortOrder,
        Integer categoryId,
        String categoryName
) {}