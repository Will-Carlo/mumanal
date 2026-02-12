package com.mumanal.shared.bootstrap;

import com.mumanal.shared.domain.repository.SystemParameterRepository;
import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import com.mumanal.shared.persistence.entity.GenParameterEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(2)
public class ParameterDataSeeder implements CommandLineRunner {

    private final SystemParameterRepository systemParameterRepository;

    private static final String SYSTEM_USER = "SYSTEM_SEEDER";

    public ParameterDataSeeder(SystemParameterRepository systemParameterRepository) {
        this.systemParameterRepository = systemParameterRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Evitar duplicados si ya corrió
        if (systemParameterRepository.count() > 0) {
            return;
        }

        System.out.println("⚙️ Initiating System Parameters Seeding...");

        // ============================================================================================
        // 1. CLIENTES (Prefijo 700)
        // ============================================================================================
        GenParameterCategoryEntity catGender = createCategory("GENDER_TYPE", "Género del cliente");
        createParameter(catGender, 721, "Masculino", "M", 1);
        createParameter(catGender, 722, "Femenino", "F", 2);
        createParameter(catGender, 723, "Otro", "Prefiero no decirlo", 3);

        // ============================================================================================
        // 3. FINANCIERO - MÉTODOS DE PAGO (Prefijo 400)
        // ============================================================================================
        GenParameterCategoryEntity catPayMethod = createCategory("PAYMENT_METHOD", "Métodos de Pago");
        createParameter(catPayMethod, 401, "Código QR", "Pago digital rápido", 1);
        createParameter(catPayMethod, 402, "Transferencia Bancaria", "Depósito directo a cuenta", 2);
        createParameter(catPayMethod, 403, "Efectivo", "Pago físico en caja", 3);
        createParameter(catPayMethod, 404, "Tarjeta Débito/Crédito", "POS Físico", 4);
        createParameter(catPayMethod, 405, "Cheque", "Cheque", 5);

        // ============================================================================================
        // 4. ESTADOS DE TRANSACCIÓN (Prefijo 500)
        // ============================================================================================
        GenParameterCategoryEntity catTxStatus = createCategory("TRANSACTION_STATUS", "Estados de Transacción");
        createParameter(catTxStatus, 501, "Pendiente", "Recibido pero no verificado", 1);
        createParameter(catTxStatus, 502, "Verificado", "Confirmado en banco", 2);
        createParameter(catTxStatus, 503, "Rechazado", "No coincide o inválido", 3);
        createParameter(catTxStatus, 504, "Devolución", "El dinero se devolvió al cliente", 4);

        // ============================================================================================
        // 8. ESTADOS DE USUARIO (Prefijo 900)
        // ============================================================================================
        GenParameterCategoryEntity catUserStatus = createCategory("USER_STATUS", "Estados de Usuario");
        createParameter(catUserStatus, 901, "Activo", "Usuario habilitado.", 1);
        createParameter(catUserStatus, 902, "Inactivo", "Desactivado temporalmente.", 2);
        createParameter(catUserStatus, 903, "Suspendido", "Acceso bloqueado por administración.", 3);
        createParameter(catUserStatus, 904, "Pendiente", "Falta verificación de datos/correo.", 4);
        createParameter(catUserStatus, 905, "Baja Definitiva", "Eliminado lógicamente (Histórico).", 5);

        System.out.println("✅ System Parameters Seeding Completed.");
    }

    // --------------------------------------------------------------------------------------------
    // HELPERS
    // --------------------------------------------------------------------------------------------

    private GenParameterCategoryEntity createCategory(String code, String name) {
        GenParameterCategoryEntity category = new GenParameterCategoryEntity();
        category.setCode(code);
        category.setName(name);
        category.setStatus(true);
        category.setCreatedBy(SYSTEM_USER);
        category.setUpdatedBy(SYSTEM_USER);
        return systemParameterRepository.saveCategory(category);
    }

    private void createParameter(GenParameterCategoryEntity category, Integer numericCode, String name, String description, Integer sortOrder) {
        GenParameterEntity param = new GenParameterEntity();
        param.setNumericCode(numericCode);
        param.setName(name);
        param.setDescription(description);
        param.setSortOrder(sortOrder);
        param.setCategory(category);
        param.setStatus(true);
        param.setCreatedBy(SYSTEM_USER);
        param.setUpdatedBy(SYSTEM_USER);
        systemParameterRepository.saveParameter(param);
    }
}