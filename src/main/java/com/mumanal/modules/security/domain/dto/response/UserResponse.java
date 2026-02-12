package com.mumanal.modules.security.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Integer id,
        String username,

        // parameter
        String statusName,
        Integer statusCode,

        Boolean locked,
        Boolean disabled,
        LocalDateTime lastLoginAt,
        Integer personId,
        String personName, // "Juan Perez" (Concatenado)
        List<String> roles // ["ADMIN", "SALES"]
) {
}
