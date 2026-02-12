package com.mumanal.shared.domain.repository;

import com.mumanal.shared.persistence.entity.GenParameterEntity;

import java.util.List;
import java.util.Optional;

public interface ParameterRepository {
    GenParameterEntity saveParameter(GenParameterEntity entity);
    List<GenParameterEntity> findParametersByCategoryId(Integer categoryId);
    Optional<GenParameterEntity> findParameterById(Integer id);
    boolean existsParameterByNumericCode(Integer numericCode, Integer categoryId);
    void deleteParameter(GenParameterEntity entity); // Soft delete manejado en servicio o aquí
}
