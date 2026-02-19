package com.mumanal.modules.security.bootstrap;

import com.mumanal.modules.generic.domain.repository.PersonRepository;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.security.domain.repository.*;
import com.mumanal.modules.security.domain.repository.UserRepository;
import com.mumanal.modules.security.persistence.entity.*;
import com.mumanal.modules.security.persistence.repository.jpa.MenuPermissionJpa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class SecurityDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final AssignedRoleRepository assignedRoleRepository;
    private final PersonRepository personRepository;
    private final MenuRepository menuRepository;
    private final MenuPermissionJpa menuPermissionJpa;
    private final PasswordEncoder passwordEncoder;

    private static final String SYSTEM_USER = "SYSTEM";
    private static final Integer STATUS_ACTIVE = 901;

    // Contraseñas en application.properties
    @Value("${app.security.user.system-password}")
    private String rootUser;

    @Value("${app.security.user.wcarlo-password}")
    private String rootPassword;

    @Value("${app.security.user.marceloq-password}")
    private String marceloqPassword;

    public SecurityDataSeeder(UserRepository userRepository,
                              RoleRepository roleRepository,
                              PermissionRepository permissionRepository,
                              AssignedRoleRepository assignedRoleRepository,
                              PersonRepository personRepository,
                              MenuRepository menuRepository,
                              MenuPermissionJpa menuPermissionJpa,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.assignedRoleRepository = assignedRoleRepository;
        this.personRepository = personRepository;
        this.menuRepository = menuRepository;
        this.menuPermissionJpa = menuPermissionJpa;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepository.countNative() > 0) {
            return;
        }

        System.out.println("🌱 Initiating Travesía Security & Menu Seeding...");

        // ============================================================================================
        // 1. DEFINICIÓN DE PERMISOS
        // ============================================================================================

        // --- SEGURIDAD (Técnica) ---
        SecPermissionEntity pUsersManage = createPermission("SEC_USERS_MANAGE", "Gestionar Usuarios (Crear/Resetear Claves)");
        SecPermissionEntity pRolesManage = createPermission("SEC_ROLES_MANAGE", "Gestionar Roles y Permisos");
        SecPermissionEntity pAuditDeleted = createPermission("SEC_AUDIT_DELETED", "Ver Registros Eliminados (Auditoría)");
        SecPermissionEntity pMenuManage = createPermission("SEC_MENU_MANAGE", "Gestionar Estructura de Menús");

        // --- PARAMETROS ---
        SecPermissionEntity pParamManage = createPermission("GEN_PARAM_MANAGE", "Gestionar Configuraciones del Sistema");

        // --- FINANZAS ---
        SecPermissionEntity pVoucherRead = createPermission("FIN_VOUCHER_READ", "Ver y sacar reporte de vouchers registrados");
        SecPermissionEntity pVoucherManage = createPermission("FIN_VOUCHER_MANAGE", "Gestionar vouchers");

        SecPermissionEntity pBankRead = createPermission("FIN_BANK_READ", "Ver bancos registrados en el sistema");
        SecPermissionEntity pBankManage = createPermission("FIN_BANK_MANAGE", "Gestionar bancos");

        SecPermissionEntity pAffiliateRead = createPermission("FIN_AFFILIATE_READ", "Ver y sacar reporte de afiliados");
        SecPermissionEntity pAffiliateManage = createPermission("FIN_AFFILIATE_MANAGE", "Gestionar afiliados");

        // --- PERMISOS DE NAVEGACIÓN (ACCESS / VIEW) ---
        // Estos permisos solo sirven para que aparezca el botón en la barra lateral.
        SecPermissionEntity pGenDash = createPermission("GEN_DASHBOARD_VIEW", "Acceso al Dashboard");
        SecPermissionEntity pSecView = createPermission("SEC_MODULE_VIEW", "Acceso al Módulo Seguridad");
        SecPermissionEntity pFinView = createPermission("FIN_MODULE_VIEW", "Acceso al Módulo Finanzas");
        SecPermissionEntity pSysView = createPermission("SYS_MODULE_VIEW", "Acceso al Módulo Super Admin");

        // ============================================================================================
        // 2. ASIGNACIÓN DE ROLES
        // ============================================================================================

        // 1. ROOT (Superusuario Técnico)
        // Acceso total, incluyendo herramientas de desarrollo/auditoría.
        SecRoleEntity roleRoot = createRole("ROOT", "Super Usuario Técnico");
        assignPermissions(roleRoot, List.of(
                // Menus
                pSysView, pSecView, pFinView, pGenDash,
                // Seguridad y Sistema
                pSecView, pUsersManage, pRolesManage, pAuditDeleted, pMenuManage, pParamManage,
                // Finanzas
                pVoucherRead, pVoucherManage,
                pBankRead, pBankManage,
                pAffiliateRead, pAffiliateManage
        ));

        // 4. FINANCE (Tesorero / Contador)
        SecRoleEntity roleFinance = createRole("FINANCE", "Encargado de Finanzas");
        assignPermissions(roleFinance, List.of(
                // Menus (Necesita ver Dashboard, Finanzas y Ventas para ver el flujo de caja)
                pGenDash, pFinView,
                // Finanzas
                pVoucherRead, pVoucherManage,
                pBankRead, pBankManage,
                pAffiliateRead, pAffiliateManage
        ));

        // ============================================================================================
        // 3. MENÚS (Estructura Visual con Lucide Icons)
        // ============================================================================================

        // --- DASHBOARD ---
        SecMenuEntity mDashboard = createMenu("Dashboard", "/dashboard", "LayoutDashboard", 1, null);
        assignMenuPermissions(mDashboard, List.of(pGenDash));

        // --- FINANZAS / TESORERÍA (Solo Admin) ---
        // Aquí van los pagos a proveedores y liquidación de vendedores.
        SecMenuEntity mFinance = createMenu("Finanzas", "/finance", "Landmark", 10, null);
        assignMenuPermissions(mFinance, List.of(pFinView));

        SecMenuEntity mIncome = createMenu("Vouchers", "/finance/voucher", "receipt-text", 1, mFinance);
        assignMenuPermissions(mIncome, List.of(pVoucherManage));

        // --- SEGURIDAD (Root/Admin) ---
        SecMenuEntity mSecurity = createMenu("Seguridad", "/security", "ShieldCheck", 90, null);
        assignMenuPermissions(mSecurity, List.of(pSecView));

        SecMenuEntity mUsers = createMenu("Usuarios", "/security/users", "User", 1, mSecurity);
        assignMenuPermissions(mUsers, List.of(pUsersManage));

        SecMenuEntity mRoles = createMenu("Roles y Permisos", "/security/roles", "KeyRound", 2, mSecurity);
        assignMenuPermissions(mRoles, List.of(pRolesManage));

        SecMenuEntity mMenus = createMenu("Menús", "/security/menus", "Menu", 3, mSecurity);
        assignMenuPermissions(mMenus, List.of(pMenuManage)); // Solo Root

        SecMenuEntity mParams = createMenu("Paramétricas", "/security/parameters", "ListTree", 4, mSecurity);
        assignMenuPermissions(mParams, List.of(pParamManage));

        // ============================================================================================
        // 4. USUARIOS
        // ============================================================================================

//        // 0. system -> Bot (Tiene los 3 roles)
//        GenPersonEntity sBot = createSystemPerson("SYSTEM", "BOT", "sysbot@mumanal.com");
//
//        if (!userRepository.existsByUsername("system")) {
//            SecUserEntity uSystem = createUserBase("system", systemPassword, sBot);
//            // Roles
//            assignRoleToUser(uSystem, roleRoot);
//            assignRoleToUser(uSystem, roleFinance);
//            System.out.println("   > Usuario creado: system [ROOT]");
//        }

        // 0. system -> ROOT
        GenPersonEntity sRoot = createSystemPerson("root", "", "sysbot@mumanal.com");

        if (!userRepository.existsByUsername(rootUser)) {
            SecUserEntity uSystem = createUserBase(rootUser, rootPassword, sRoot);
            // Roles
            assignRoleToUser(uSystem, roleRoot);
            assignRoleToUser(uSystem, roleFinance);
            System.out.println("   > Usuario creado: system [ROOT]");
        }

        // 1. marceloq -> Marcelo Quiroga
        GenPersonEntity pMarceloq = createSystemPerson("Marcelo", "Quiroga", "mquiroga@mumanal.com");

        if (!userRepository.existsByUsername("marceloq")) {
            SecUserEntity uMarceloq = createUserBase("marceloq", marceloqPassword, pMarceloq);
            assignRoleToUser(uMarceloq, roleFinance);
            System.out.println("   > Usuario creado: marceloq [FINANCE]");
        }

        System.out.println("✅ Seeding completed successfully.");
    }

    // --------------------------------------------------------------------------------------------
    // HELPERS
    // --------------------------------------------------------------------------------------------

    private SecPermissionEntity createPermission(String code, String name) {
        SecPermissionEntity p = new SecPermissionEntity();
        p.setCode(code);
        p.setName(name);
        p.setStatus(true);
        p.setCreatedBy(SYSTEM_USER);
        p.setUpdatedBy(SYSTEM_USER);
        return permissionRepository.save(p);
    }

    private SecRoleEntity createRole(String name, String description) {
        SecRoleEntity r = new SecRoleEntity();
        r.setName(name);
        r.setDescription(description);
        r.setCreatedBy(SYSTEM_USER);
        r.setUpdatedBy(SYSTEM_USER);
        return roleRepository.save(r);
    }

    private void assignPermissions(SecRoleEntity role, List<SecPermissionEntity> permissions) {
        if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }
        for (SecPermissionEntity perm : permissions) {
            SecAssignedPermissionEntity association = new SecAssignedPermissionEntity();
            association.setRole(role);
            association.setPermission(perm);
            association.setCreatedBy(SYSTEM_USER);
            association.setUpdatedBy(SYSTEM_USER);
            role.getPermissions().add(association);
        }
        roleRepository.save(role);
    }

    private SecMenuEntity createMenu(String name, String route, String icon, Integer order, SecMenuEntity parent) {
        SecMenuEntity m = new SecMenuEntity();
        m.setName(name);
        m.setRoute(route);
        m.setIcon(icon);
        m.setSortOrder(order);
        m.setParentMenu(parent);
        m.setStatus(true);
        m.setCreatedBy(SYSTEM_USER);
        m.setUpdatedBy(SYSTEM_USER);
        return menuRepository.save(m);
    }

    private void assignMenuPermissions(SecMenuEntity menu, List<SecPermissionEntity> permissions) {
        for (SecPermissionEntity perm : permissions) {
            SecMenuPermissionEntity mp = new SecMenuPermissionEntity();
            mp.setMenu(menu);
            mp.setPermission(perm);
            mp.setCreatedBy(SYSTEM_USER);
            mp.setUpdatedBy(SYSTEM_USER);
            menuPermissionJpa.save(mp);
        }
    }

    private GenPersonEntity createSystemPerson(String name, String lastName, String email) {
        // En un escenario real, buscaríamos primero por email para no duplicar persona
        GenPersonEntity p = new GenPersonEntity();
        p.setFirstName(name);
        p.setPaternalSurname(lastName);
        p.setEmail(email);
        p.setPhoneNumber(0);
        p.setCreatedBy(SYSTEM_USER);
        p.setUpdatedBy(SYSTEM_USER);
        return personRepository.save(p);
    }

    private SecUserEntity createUserBase(String username, String rawPassword, GenPersonEntity person) {
        SecUserEntity user = new SecUserEntity();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setPerson(person);
        user.setLocked(false);
        user.setDisabled(false);
        user.setStatusType(STATUS_ACTIVE); // Activo
        user.setCreatedBy(SYSTEM_USER);
        user.setUpdatedBy(SYSTEM_USER);
        return userRepository.save(user);
    }

    private void assignRoleToUser(SecUserEntity user, SecRoleEntity role) {
        SecAssignedRoleEntity assignment = new SecAssignedRoleEntity();
        assignment.setUser(user);
        assignment.setRole(role);
        assignment.setGrantedBy(SYSTEM_USER);
        assignment.setUpdatedBy(SYSTEM_USER);
        assignment.setGrantedDate(LocalDateTime.now());
        assignedRoleRepository.save(assignment);
    }
}