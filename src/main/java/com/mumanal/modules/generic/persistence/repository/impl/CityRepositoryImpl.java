package com.mumanal.modules.generic.persistence.repository.impl;

import com.mumanal.modules.generic.domain.repository.CityRepository;
import com.mumanal.modules.generic.persistence.entity.GenCityEntity;
import com.mumanal.modules.generic.persistence.repository.jpa.CityJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private final CityJpa cityJpa;

    public CityRepositoryImpl(CityJpa cityJpa) {
        this.cityJpa = cityJpa;
    }

    @Override
    public List<GenCityEntity> findAll() { return cityJpa.findAllByEnabledTrue(); }

    @Override
    public Optional<GenCityEntity> findById(Integer id) { return cityJpa.findByIdAndEnabledTrue(id); }

    @Override
    public GenCityEntity save(GenCityEntity entity) { return cityJpa.save(entity); }

    @Override
    public boolean existsByNameAndCountry(String name, String country) {
        return cityJpa.existsByNameAndCountryAndEnabledTrue(name, country);
    }

    @Override
    public boolean existsByNameAndCountryAndIdNot(String name, String country, Integer id) {
        return cityJpa.existsByNameAndCountryAndIdNotAndEnabledTrue(name, country, id);
    }

    @Override
    public long nativeCount() {
        return cityJpa.count();
    }

    @Override
    public Optional<GenCityEntity> findByName(String name) {
        return cityJpa.findByNameAndEnabledTrue(name);
    }
}