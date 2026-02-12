package com.mumanal.modules.security.domain.dto.response;

public record MenuPermissionResponse(
        Integer id,
        Integer menuId,
        String menuName,
        Integer permissionId,
        String permissionCode,
        String permissionName
) {}