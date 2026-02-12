package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecAssignedPermissionEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RolePermissionJpa extends ListCrudRepository<SecAssignedPermissionEntity, Integer> {
    List<SecAssignedPermissionEntity> findAllByPermissionIdAndEnabledTrue(Integer permissionId);
    void deleteAllByRoleIdAndEnabledTrue(Integer roleId);
}