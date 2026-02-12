package com.mumanal.modules.security.domain.dto.response;

import java.time.LocalDateTime;

public record AssignmentResponse(
        Integer id,
        Integer userId,
        String username,
        Integer roleId,
        String roleName,
        String grantedBy,
        LocalDateTime grantedDate,
        LocalDateTime expirationDate,
        Boolean status
) {}