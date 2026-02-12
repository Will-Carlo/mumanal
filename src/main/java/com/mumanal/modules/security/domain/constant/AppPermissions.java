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
    public static final String COM_MODULE_VIEW    = "hasAuthority('COM_MODULE_VIEW')";
    public static final String SAL_MODULE_VIEW    = "hasAuthority('SAL_MODULE_VIEW')";
    public static final String FIN_MODULE_VIEW    = "hasAuthority('FIN_MODULE_VIEW')";

    // ========================================================================
    // 1. SYSTEM / TENANT (Super Admin)
    // ========================================================================
    public static final String GEN_COMPANY_READ   = "hasAuthority('GEN_COMPANY_READ')";
    public static final String GEN_COMPANY_MANAGE = "hasAuthority('GEN_COMPANY_MANAGE')";

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
    // 4. INVENTARIOS (INV)
    // ========================================================================
    public static final String INV_PRODUCT_READ   = "hasAuthority('INV_PRODUCT_READ')";
    public static final String INV_PRODUCT_MANAGE = "hasAuthority('INV_PRODUCT_MANAGE')";
    public static final String INV_PRODUCT_STATUS = "hasAuthority('INV_PRODUCT_STATUS')"; // Activar/Inactivar

    public static final String INV_PROVIDER_READ  = "hasAuthority('INV_PROVIDER_READ')";
    public static final String INV_PROVIDER_MANAGE= "hasAuthority('INV_PROVIDER_MANAGE')";

    public static final String INV_LOCATION_READ  = "hasAuthority('INV_LOCATION_READ')";
    public static final String INV_LOCATION_MANAGE= "hasAuthority('INV_LOCATION_MANAGE')";

    // ========================================================================
    // 5. COMERCIAL (COM)
    // ========================================================================
    public static final String COM_PACKAGE_READ   = "hasAuthority('COM_PACKAGE_READ')";
    public static final String COM_PACKAGE_MANAGE = "hasAuthority('COM_PACKAGE_MANAGE')";
    public static final String COM_PACKAGE_PUBLISH= "hasAuthority('COM_PACKAGE_PUBLISH')";

    public static final String COM_SELLER_READ    = "hasAuthority('COM_SELLER_READ')";
    public static final String COM_SELLER_MANAGE  = "hasAuthority('COM_SELLER_MANAGE')"; // Metas de vendedores

    public static final String COM_CLIENT_READ    = "hasAuthority('COM_CLIENT_READ')";
    public static final String COM_CLIENT_MANAGE  = "hasAuthority('COM_CLIENT_MANAGE')";

    // ========================================================================
    // 6. VENTAS (SAL) - RESERVAS
    // ========================================================================
    public static final String SAL_RESERVATION_READ   = "hasAuthority('SAL_RESERVATION_READ')";
    public static final String SAL_RESERVATION_CREATE = "hasAuthority('SAL_RESERVATION_CREATE')";
    public static final String SAL_RESERVATION_UPDATE = "hasAuthority('SAL_RESERVATION_UPDATE')";
    public static final String SAL_RESERVATION_DELETE = "hasAuthority('SAL_RESERVATION_DELETE')"; // Anular
    public static final String SAL_AMOUNT_UPDATE      = "hasAuthority('SAL_AMOUNT_UPDATE')"; // Cambiar precio manual

    // ========================================================================
    // 7. FINANZAS (SAL/FIN) - PAGOS Y FLUJOS
    // ========================================================================
    // Ingresos (Cobros a Clientes)
    public static final String SAL_PAYMENT_READ   = "hasAuthority('SAL_PAYMENT_READ')";
    public static final String SAL_PAYMENT_CREATE = "hasAuthority('SAL_PAYMENT_CREATE')";
    public static final String SAL_PAYMENT_UPDATE = "hasAuthority('SAL_PAYMENT_UPDATE')";
    public static final String SAL_PAYMENT_DELETE = "hasAuthority('SAL_PAYMENT_DELETE')";

    // Egresos (Liquidación Vendedores)
    public static final String SAL_COMMISSION_READ = "hasAuthority('SAL_COMMISSION_READ')";
    public static final String SAL_COMMISSION_MANAGE = "hasAuthority('SAL_COMMISSION_MANAGE')"; // Recalcular
    public static final String SAL_SELLER_PAYMENT_READ = "hasAuthority('SAL_SELLER_PAYMENT_READ')";
    public static final String SAL_SELLER_PAYMENT_MANAGE = "hasAuthority('SAL_SELLER_PAYMENT_MANAGE')"; // Pagar

    // Egresos (Pago a Proveedores)
    public static final String SAL_PROVIDER_PAYMENT_READ   = "hasAuthority('SAL_PROVIDER_PAYMENT_READ')";
    public static final String SAL_PROVIDER_PAYMENT_MANAGE = "hasAuthority('SAL_PROVIDER_PAYMENT_MANAGE')";

    // Ingresos (Transsacciones)
    public static final String SAL_TRANSACTION_READ   = "hasAuthority('SAL_TRANSACTION_READ')";
    public static final String SAL_TRANSACTION_MANAGE = "hasAuthority('SAL_TRANSACTION_MANAGE')";

    // ========================================================================
    // HELPERS / COMBINACIONES ÚTILES
    // ========================================================================
    public static final String IS_AUTHENTICATED = "isAuthenticated()";

    // Ejemplo: Alguien que pueda ver reservas (Vendedor) O Gestionarlas (Admin)
    public static final String CAN_VIEW_OR_MANAGE_RESERVATIONS =
            "hasAuthority('SAL_RESERVATION_READ') or hasAuthority('SAL_RESERVATION_UPDATE')";
}