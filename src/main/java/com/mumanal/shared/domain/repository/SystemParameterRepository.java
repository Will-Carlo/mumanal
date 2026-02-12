package com.mumanal.shared.domain.repository;

import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import com.mumanal.shared.persistence.entity.GenParameterEntity;

import java.util.List;
import java.util.Optional;

public interface SystemParameterRepository {
    // Categories
    GenParameterCategoryEntity saveCategory(GenParameterCategoryEntity entity);
    List<GenParameterCategoryEntity> findAllCategories();
    Optional<GenParameterCategoryEntity> findCategoryById(Integer id);
    boolean existsCategoryByCode(String code);
    Optional<GenParameterCategoryEntity> findCategoryByCode(String code);

    // Parameters
    GenParameterEntity saveParameter(GenParameterEntity entity);
    List<GenParameterEntity> findParametersByCategoryId(Integer categoryId);
    Optional<GenParameterEntity> findParameterById(Integer id);
    boolean existsParameterByNumericCode(Integer numericCode, Integer categoryId);
    void deleteParameter(GenParameterEntity entity); // Soft delete manejado en servicio o aquí

    int count();

    Optional<GenParameterEntity> findParameterByNumericCodeAndCategoryCode(Integer numericCode, String categoryCode);
    boolean existsParameterByNumericCodeAndCategoryCode(Integer numericCode, String categoryCode);
    Optional<GenParameterEntity> findParameterByNumericCode(Integer numericCode);
}