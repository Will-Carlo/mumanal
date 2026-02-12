package com.mumanal.shared.persistence.repository.jpa;

import com.mumanal.shared.persistence.entity.GenParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParameterJpa extends JpaRepository<GenParameterEntity, Integer> {
    List<GenParameterEntity> findAllByCategoryIdOrderBySortOrderAsc(Integer categoryId);

    // Validar duplicado de código numérico DENTRO de la misma categoría
    boolean existsByNumericCodeAndCategoryId(Integer numericCode, Integer categoryId);

    // Usamos @Query para evitar problemas con nombres largos o relaciones
    @Query("SELECT COUNT(p) > 0 FROM GenParameterEntity p WHERE p.numericCode = :numericCode AND p.category.code = :categoryCode")
    boolean existsByNumericCodeAndCategoryCode(@Param("numericCode") Integer numericCode, @Param("categoryCode") String categoryCode);

    @Query("SELECT p FROM GenParameterEntity p WHERE p.numericCode = :numericCode AND p.category.code = :categoryCode")
    Optional<GenParameterEntity> findByNumericCodeAndCategoryCode(@Param("numericCode") Integer numericCode, @Param("categoryCode") String categoryCode);

    // Búsqueda directa y eficiente por el código único
    Optional<GenParameterEntity> findByNumericCode(Integer numericCode);
}