package com.mumanal.shared.persistence.repository.impl;

import com.mumanal.shared.domain.repository.SystemParameterRepository;
import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import com.mumanal.shared.persistence.entity.GenParameterEntity;
import com.mumanal.shared.persistence.repository.jpa.ParameterCategoryJpa;
import com.mumanal.shared.persistence.repository.jpa.ParameterJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SystemParameterRepositoryImpl implements SystemParameterRepository {

    private final ParameterCategoryJpa categoryJpa;
    private final ParameterJpa parameterJpa;

    public SystemParameterRepositoryImpl(ParameterCategoryJpa categoryJpa, ParameterJpa parameterJpa) {
        this.categoryJpa = categoryJpa;
        this.parameterJpa = parameterJpa;
    }

    // --- Categories ---
    @Override
    public GenParameterCategoryEntity saveCategory(GenParameterCategoryEntity entity) {
        return categoryJpa.save(entity);
    }
    @Override
    public List<GenParameterCategoryEntity> findAllCategories() {
        return categoryJpa.findAll();
    }
    @Override
    public Optional<GenParameterCategoryEntity> findCategoryById(Integer id) {
        return categoryJpa.findById(id);
    }
    @Override
    public boolean existsCategoryByCode(String code) {
        return categoryJpa.existsByCodeAndEnabledTrue(code);
    }
    @Override
    public Optional<GenParameterCategoryEntity> findCategoryByCode(String code) {
        return categoryJpa.findByCodeAndEnabledTrue(code);
    }
    // --- Parameters ---
    @Override
    public GenParameterEntity saveParameter(GenParameterEntity entity) {
        return parameterJpa.save(entity);
    }
    @Override
    public List<GenParameterEntity> findParametersByCategoryId(Integer categoryId) {
        return parameterJpa.findAllByCategoryIdOrderBySortOrderAsc(categoryId);
    }
    @Override
    public Optional<GenParameterEntity> findParameterById(Integer id) {
        return parameterJpa.findById(id);
    }
    @Override
    public boolean existsParameterByNumericCode(Integer numericCode, Integer categoryId) {
        return parameterJpa.existsByNumericCodeAndCategoryId(numericCode, categoryId);
    }
    @Override
    public void deleteParameter(GenParameterEntity entity) {
        parameterJpa.save(entity);
    }

    @Override
    public int count(){
        return parameterJpa.findAll().size();
    }

    @Override
    public Optional<GenParameterEntity> findParameterByNumericCodeAndCategoryCode(Integer numericCode, String  categoryCode) {
        return parameterJpa.findByNumericCodeAndCategoryCode(numericCode, categoryCode);
    }

    @Override
    public boolean existsParameterByNumericCodeAndCategoryCode(Integer numericCode, String categoryCode) {
        return parameterJpa.existsByNumericCodeAndCategoryCode(numericCode, categoryCode);
    }

    @Override
    public Optional<GenParameterEntity> findParameterByNumericCode(Integer numericCode) {
        return parameterJpa.findByNumericCode(numericCode);
    }

}