package com.mumanal.modules.security.domain.dto.response;

public record PermissionResponse(
        Integer id,
        String code,
        String name,
        String description
) {}