package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.PermissionRepository;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import com.mumanal.modules.security.persistence.repository.jpa.PermissionJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpa permissionJpa;

    public PermissionRepositoryImpl(PermissionJpa permissionJpa) {
        this.permissionJpa = permissionJpa;
    }

    @Override
    public List<SecPermissionEntity> findAll() {
        return permissionJpa.findAllByEnabledTrue();
    }

    @Override
    public Optional<SecPermissionEntity> findById(Integer id) {
        return permissionJpa.findByIdAndEnabledTrue(id);
    }

    @Override
    public SecPermissionEntity save(SecPermissionEntity entity) {
        return permissionJpa.save(entity);
    }

    @Override
    public boolean existsByCode(String code) {
        return permissionJpa.existsByCodeAndEnabledTrue(code);
    }

    @Override
    public boolean existsById(Integer id) {
        return permissionJpa.existsById(id);
    }
}
