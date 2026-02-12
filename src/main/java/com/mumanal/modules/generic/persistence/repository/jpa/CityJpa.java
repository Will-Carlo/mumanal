package com.mumanal.modules.generic.persistence.repository.jpa;

import com.mumanal.modules.generic.persistence.entity.GenCityEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CityJpa extends ListCrudRepository<GenCityEntity, Integer> {
    // Soft Delete: Solo habilitados
    List<GenCityEntity> findAllByEnabledTrue();
    Optional<GenCityEntity> findByIdAndEnabledTrue(Integer id);

    // Validar duplicados por PAR (Nombre + País)
    // Ej: No permitimos crear "Tarija" en "Bolivia" dos veces.
    boolean existsByNameAndCountryAndEnabledTrue(String name, String country);
    boolean existsByNameAndCountryAndIdNotAndEnabledTrue(String name, String country, Integer id);
    Optional<GenCityEntity> findByNameAndEnabledTrue(String name);
}