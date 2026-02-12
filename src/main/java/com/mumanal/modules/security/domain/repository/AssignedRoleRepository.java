package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;

import java.util.List;
import java.util.Optional;

public interface AssignedRoleRepository {
    List<SecAssignedRoleEntity> findAll();
    List<SecAssignedRoleEntity> findAllByUser(Integer userId);
    Optional<SecAssignedRoleEntity> findById(Integer id);
    SecAssignedRoleEntity save(SecAssignedRoleEntity entity);

    boolean existsAssignment(Integer userId, Integer roleId);
}