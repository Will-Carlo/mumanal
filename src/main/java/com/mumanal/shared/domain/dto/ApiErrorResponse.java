package com.mumanal.shared.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        String code,
        String message,
        List<ValidationError> errors,
        LocalDateTime timestamp
) {
    // simple error
    public ApiErrorResponse(String code, String message) {
        this(code, message, null, LocalDateTime.now());
    }

    // validation error
    public ApiErrorResponse(String code, String message, List<ValidationError> errors) {
        this(code, message, errors, LocalDateTime.now());
    }
}
