package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssignedRoleJpa extends ListCrudRepository<SecAssignedRoleEntity, Integer> {
    List<SecAssignedRoleEntity> findAllByUserIdAndEnabledTrue(Integer userId);
    List<SecAssignedRoleEntity> findAllByRoleIdAndEnabledTrue(Integer roleId);
    List<SecAssignedRoleEntity> findAllByExpirationDateBefore(LocalDateTime now);

    List<SecAssignedRoleEntity> findAllByEnabledTrue();
    Optional<SecAssignedRoleEntity> findByIdAndEnabledTrue(Integer id);

    boolean existsByUserIdAndRoleIdAndEnabledTrue(Integer userId, Integer roleId);
}