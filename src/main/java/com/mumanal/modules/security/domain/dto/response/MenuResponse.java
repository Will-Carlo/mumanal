package com.mumanal.modules.security.domain.dto.response;

import java.util.List;

public record MenuResponse(
        Integer id,
        String name,
        String route,
        String icon,

        List<MenuResponse> children
) {}