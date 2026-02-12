package com.mumanal.modules.security.domain.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserRequest(
        Integer statusType, // 901=Activo, 902=Inactivo, etc.
        Boolean locked,
        Boolean disabled,

        @NotNull
        List<Integer> roleIds // La nueva lista completa de roles (reemplaza a la anterior)
) {}