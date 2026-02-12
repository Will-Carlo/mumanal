package com.mumanal.shared.domain.dto;

public record ValidationError(
        String field,
        String reason
) {
}
