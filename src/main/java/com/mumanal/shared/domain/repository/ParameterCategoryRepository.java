package com.mumanal.shared.domain.repository;

import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ParameterCategoryRepository {
    GenParameterCategoryEntity saveCategory(GenParameterCategoryEntity entity);
    List<GenParameterCategoryEntity> findAllCategories();
    Optional<GenParameterCategoryEntity> findCategoryById(Integer id);
    boolean existsCategoryByCode(String code);

}
