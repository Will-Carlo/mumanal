package com.mumanal.modules.generic.bootstrap;

import com.mumanal.modules.generic.domain.repository.CityRepository;
import com.mumanal.modules.generic.persistence.entity.GenCityEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Order(4)
public class CityDataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private static final String SEEDER_USER = "SYSTEM";

    public CityDataSeeder(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos para no duplicar
        if (cityRepository.nativeCount() > 0) {
            return;
        }

        System.out.println("🌍 Initiating Cities (Departments) Seeding...");

        List<String> bolivianCities = Arrays.asList(
                "La Paz",           // Departamento: La Paz
                "Santa Cruz",       // Departamento: Santa Cruz
                "Cochabamba",       // Departamento: Cochabamba
                "Chuquisaca/Sucre",            // Departamento: Chuquisaca
                "Oruro",            // Departamento: Oruro
                "Potosí",           // Departamento: Potosí
                "Tarija",           // Departamento: Tarija
                "Beni/Trinidad",         // Departamento: Beni
                "Pando/Cobija"            // Departamento: Pando
        );

        for (String cityName : bolivianCities) {
            createCity(cityName, "Bolivia");
        }

        System.out.println("✅ Cities Seeding Completed Successfully.");
    }

    private void createCity(String name, String country) {
        GenCityEntity city = new GenCityEntity();
        city.setName(name);
        city.setCountry(country);
        city.setCreatedBy(SEEDER_USER);
        city.setUpdatedBy(SEEDER_USER);
        cityRepository.save(city);
    }
}