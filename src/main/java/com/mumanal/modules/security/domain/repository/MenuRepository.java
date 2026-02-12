package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecMenuEntity;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    List<SecMenuEntity> findAll();
    List<SecMenuEntity> findAllActive();
    Optional<SecMenuEntity> findById(Integer id);
    Optional<SecMenuEntity> findByIdNative(Integer id); // Incluye eliminados lógicos

    SecMenuEntity save(SecMenuEntity entity);
    boolean existsById(Integer id);

    // Validaciones de unicidad (Scope: Mismo Padre)
    boolean existsByNameAndParentId(String name, Integer parentId);
    boolean existsByNameAndParentIdAndIdNot(String name, Integer parentId, Integer id);
}