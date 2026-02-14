package com.mumanal.modules.finance.bootstrap;

import com.mumanal.modules.finance.domain.repository.BankRepository;
import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Order(5) // Se ejecuta después de las Ciudades (Order 4)
public class BankDataSeeder implements CommandLineRunner {

    private final BankRepository bankRepository;
    private static final String SEEDER_USER = "SYSTEM";

    public BankDataSeeder(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos usando findAll() o count() según tu repo
        if (!bankRepository.findAll().isEmpty()) {
            return;
        }

        System.out.println("🏦 Initiating Bolivian Banks Seeding...");

        // Lista de Bancos y Entidades Financieras de Bolivia
        // Formato: Nombre Comercial | Código (Key para la IA)
        List<BankSeedDto> bolivianBanks = Arrays.asList(
                // --- BANCOS MÚLTIPLES ---
                new BankSeedDto("Banco Unión S.A.", "UNION"),
                new BankSeedDto("Banco Nacional de Bolivia S.A.", "BNB"),
                new BankSeedDto("Banco Mercantil Santa Cruz S.A.", "BMSC"), // A veces la IA lo ve como MERCANTIL
                new BankSeedDto("Banco BISA S.A.", "BISA"),
                new BankSeedDto("Banco de Crédito de Bolivia S.A.", "BCP"),
                new BankSeedDto("Banco Económico S.A.", "ECONOMICO"),
                new BankSeedDto("Banco Ganadero S.A.", "GANADERO"),
                new BankSeedDto("Banco Solidario S.A.", "BANSOL"), // Banco Sol
                new BankSeedDto("Banco FIE S.A.", "FIE"),
                new BankSeedDto("Banco Fortaleza S.A.", "FORTALEZA"),
                new BankSeedDto("Banco Prodem S.A.", "PRODEM"),

                // --- BANCOS PYME ---
                new BankSeedDto("Banco Pyme Ecofuturo S.A.", "ECOFUTURO"),
                new BankSeedDto("Banco Pyme de la Comunidad S.A.", "COMUNIDAD"),

                // --- ENTIDADES FINANCIERAS DE VIVIENDA (Ex Mutuales) ---
                new BankSeedDto("La Primera E.F.V.", "LA_PRIMERA"),
                new BankSeedDto("La Promotora E.F.V.", "LA_PROMOTORA"),
                new BankSeedDto("El Progreso E.F.V.", "EL_PROGRESO"),

                // --- BANCA ESTATAL / DESARROLLO ---
                new BankSeedDto("Banco de Desarrollo Productivo S.A.M.", "BDP"),

                // --- COOPERATIVAS (Principales) ---
                new BankSeedDto("Cooperativa Jesús Nazareno", "JESUS_NAZARENO"),
                new BankSeedDto("Cooperativa Fátima", "FATIMA"),
                new BankSeedDto("Cooperativa San Martín de Porres", "SAN_MARTIN"),
                new BankSeedDto("Cooperativa San Pedro", "SAN_PEDRO"),
                new BankSeedDto("Cooperativa Loyola", "LOYOLA"),
                new BankSeedDto("Cooperativa San Antonio", "SAN_ANTONIO"),
                new BankSeedDto("Cooperativa Inca Huasi", "INCA_HUASI"),
                new BankSeedDto("Cooperativa Comarapa", "COMARAPA"),
                new BankSeedDto("Cooperativa El Buen Samaritano", "BUEN_SAMARITANO"),

                // --- IFD (Instituciones Financieras de Desarrollo) ---
                new BankSeedDto("Crecer IFD", "CRECER"),
                new BankSeedDto("Diaconía IFD", "DIACONIA"),
                new BankSeedDto("Pro Mujer IFD", "PRO_MUJER"),
                new BankSeedDto("Sembrar Sartawi IFD", "SARTAWI"),
                new BankSeedDto("Idepro IFD", "IDEPRO")
        );

        for (BankSeedDto dto : bolivianBanks) {
            createBank(dto.name, dto.code);
        }

        System.out.println("✅ Banks Seeding Completed Successfully.");
    }

    private void createBank(String name, String code) {
        // Verificar doble seguridad por si se ejecuta en entornos concurrentes
        if (!bankRepository.existsByBankCode(code)) {
            FinBankEntity bank = new FinBankEntity();
            bank.setName(name);
            bank.setBankCode(code); // Importante para el match con la IA
            bank.setCreatedBy(SEEDER_USER);
            bank.setUpdatedBy(SEEDER_USER);
            bankRepository.save(bank);
        }
    }

    // Helper Record para organizar la data dentro del seeder
    private record BankSeedDto(String name, String code) {}
}