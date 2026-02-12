package com.mumanal.modules.security.domain.dto.response;

import java.util.Set;

public record UserProfileResponse(
        Integer id,
        String username,
        String firstName,
        String paternalLastName,
        String maternalLastName,
        String fullName,
        String email,
        String imageUrl,
        Set<String> roles,
        Set<String> permissions
) {}