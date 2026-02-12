package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    List<SecPermissionEntity> findAll();
    Optional<SecPermissionEntity> findById(Integer id);
    SecPermissionEntity save(SecPermissionEntity entity);
    boolean existsByCode(String code);
    boolean existsById(Integer id);
}
