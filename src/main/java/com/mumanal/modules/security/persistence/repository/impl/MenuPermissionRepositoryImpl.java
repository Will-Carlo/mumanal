package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.MenuPermissionRepository;
import com.mumanal.modules.security.persistence.entity.SecMenuPermissionEntity;
import com.mumanal.modules.security.persistence.repository.jpa.MenuPermissionJpa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuPermissionRepositoryImpl implements MenuPermissionRepository {
    private final MenuPermissionJpa jpa;

    public MenuPermissionRepositoryImpl(MenuPermissionJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public SecMenuPermissionEntity save(SecMenuPermissionEntity entity) {
        return jpa.save(entity);
    }

    @Override
    public List<SecMenuPermissionEntity> findAllByMenuId(Integer menuId) {
        return jpa.findAllByMenuIdAndEnabledTrue(menuId);
    }

    @Override
    public boolean existsByMenuIdAndPermissionId(Integer menuId, Integer permissionId) {
        return jpa.existsByMenuIdAndPermissionIdAndEnabledTrue(menuId, permissionId);
    }

    @Override
    public void deleteByMenuAndPermission(Integer menuId, Integer permissionId) {
        jpa.deleteByMenuIdAndPermissionId(menuId, permissionId);
    }

    @Override
    public void deleteById(Integer id) {
        jpa.deleteByIdNative(id);
    }
}