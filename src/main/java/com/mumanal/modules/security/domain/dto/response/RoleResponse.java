package com.mumanal.modules.security.domain.dto.response;

import java.util.List;

public record RoleResponse(
        Integer id,
        String name,
        String description,
        List<String> permissions // (Ej: ["SALE_READ", "SALE_CREATE"])
) {}