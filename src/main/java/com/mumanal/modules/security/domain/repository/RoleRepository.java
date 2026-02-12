package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecRoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<SecRoleEntity> findAll();
    Optional<SecRoleEntity> findById(Integer id);
    Optional<SecRoleEntity> findByIdNative(Integer id); // Para recover

    SecRoleEntity save(SecRoleEntity entity);

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
    long countNative();
}