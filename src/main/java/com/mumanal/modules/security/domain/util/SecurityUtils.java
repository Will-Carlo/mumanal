package com.mumanal.modules.security.domain.util;

import com.mumanal.modules.security.domain.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    /**
     * Obtiene el ID de la empresa del usuario logueado actualmente.
     * @return Integer companyId
     * @throws RuntimeException si no hay usuario autenticado.
     */
    public Integer getCurrentCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si llegamos aquí es un error grave de lógica (endpoint público accediendo a datos privados)
        throw new IllegalStateException("No authenticated user found or user does not belong to a company");
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return "SYSTEM";
    }

    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;

        // Spring Security suele guardar roles como "ROLE_ADMIN", asegúrate del prefijo
        String roleToCheck = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(roleToCheck));
    }
}