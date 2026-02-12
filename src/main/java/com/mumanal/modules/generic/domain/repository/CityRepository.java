package com.mumanal.modules.generic.domain.repository;

import com.mumanal.modules.generic.persistence.entity.GenCityEntity;

import java.util.List;
import java.util.Optional;

public interface CityRepository {
    List<GenCityEntity> findAll();
    Optional<GenCityEntity> findById(Integer id);
    GenCityEntity save(GenCityEntity entity);

    boolean existsByNameAndCountry(String name, String country);
    boolean existsByNameAndCountryAndIdNot(String name, String country, Integer id);
    long nativeCount();
    Optional<GenCityEntity> findByName(String name);
}