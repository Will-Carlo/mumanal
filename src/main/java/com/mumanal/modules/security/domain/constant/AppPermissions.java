package com.mumanal.modules.security.domain.constant;

/**
 * Catálogo centralizado de permisos del sistema.
 * Contiene las expresiones SpEL completas para uso directo en @PreAuthorize.
 * Mantiene consistencia con los códigos definidos en SecurityDataSeeder.
 */
public final class AppPermissions {

    private AppPermissions() {}

    // ========================================================================
    // 0. NAVEGACIÓN / MÓDULOS (VIEW)
    // ========================================================================
    public static final String GEN_DASHBOARD_VIEW = "hasAuthority('GEN_DASHBOARD_VIEW')";
    public static final String SYS_MODULE_VIEW    = "hasAuthority('SYS_MODULE_VIEW')";
    public static final String SEC_MODULE_VIEW    = "hasAuthority('SEC_MODULE_VIEW')";
    public static final String FIN_MODULE_VIEW    = "hasAuthority('FIN_MODULE_VIEW')";

    // ========================================================================
    // 2. SEGURIDAD TÉCNICA (SEC)
    // ========================================================================
    public static final String SEC_USERS_MANAGE   = "hasAuthority('SEC_USERS_MANAGE')"; // Crear/Resetear Usuarios
    public static final String SEC_ROLES_MANAGE   = "hasAuthority('SEC_ROLES_MANAGE')"; // Roles y Permisos
    public static final String SEC_AUDIT_DELETED  = "hasAuthority('SEC_AUDIT_DELETED')"; // Auditoría Soft Delete
    public static final String SEC_MENU_MANAGE    = "hasAuthority('SEC_MENU_MANAGE')"; // Estructura Menús

    // ========================================================================
    // 3. PARAMETROS GLOBALES (GEN)
    // ========================================================================
    public static final String GEN_PARAM_MANAGE   = "hasAuthority('GEN_PARAM_MANAGE')";

    // ========================================================================
    // 7. FINANZAS (SAL/FIN) - PAGOS Y FLUJOS
    // ========================================================================
    // Ingresos (Cobros a Clientes)
    public static final String FIN_VOUCHER_READ   = "hasAuthority('FIN_VOUCHER_READ')";
    public static final String FIN_VOUCHER_MANAGE = "hasAuthority('FIN_VOUCHER_MANAGE')";

    public static final String FIN_BANK_READ = "hasAuthority('FIN_BANK_READ')";
    public static final String FIN_BANK_MANAGE = "hasAuthority('FIN_BANK_MANAGE')";

    public static final String FIN_AFFILIATE_READ = "hasAuthority('FIN_AFFILIATE_READ')";
    public static final String FIN_AFFILIATE_MANAGE = "hasAuthority('FIN_AFFILIATE_MANAGE')";

    // ========================================================================
    // HELPERS / COMBINACIONES ÚTILES
    // ========================================================================
    public static final String IS_AUTHENTICATED = "isAuthenticated()";

    // Ejemplo: Alguien que pueda ver reservas (Vendedor) O Gestionarlas (Admin)
    public static final String CAN_VIEW_OR_MANAGE_RESERVATIONS =
            "hasAuthority('SAL_RESERVATION_READ') or hasAuthority('SAL_RESERVATION_UPDATE')";
}