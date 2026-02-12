package com.mumanal.modules.security.domain.dto.request;

public record LoginRequest(
        String username,
        String password
) {
}
