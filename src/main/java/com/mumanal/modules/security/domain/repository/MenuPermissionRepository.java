package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecMenuPermissionEntity;

import java.util.List;

public interface MenuPermissionRepository {
    SecMenuPermissionEntity save(SecMenuPermissionEntity entity);
    List<SecMenuPermissionEntity> findAllByMenuId(Integer menuId);
    boolean existsByMenuIdAndPermissionId(Integer menuId, Integer permissionId);
    void deleteByMenuAndPermission(Integer menuId, Integer permissionId);
    void deleteById(Integer id);
}