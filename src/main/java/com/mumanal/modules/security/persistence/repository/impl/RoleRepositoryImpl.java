package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.RoleRepository;
import com.mumanal.modules.security.persistence.entity.SecRoleEntity;
import com.mumanal.modules.security.persistence.repository.jpa.RoleJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpa roleJpa;

    public RoleRepositoryImpl(RoleJpa roleJpa) {
        this.roleJpa = roleJpa;
    }

    @Override
    public List<SecRoleEntity> findAll() {
        return roleJpa.findAllByEnabledTrue();
    }

    @Override
    public Optional<SecRoleEntity> findById(Integer id) {
        return roleJpa.findByIdAndEnabledTrue(id);
    }

    @Override
    public Optional<SecRoleEntity> findByIdNative(Integer id) {
        return roleJpa.findById(id);
    }

    @Override
    public SecRoleEntity save(SecRoleEntity entity) {
        return roleJpa.save(entity);
    }

    @Override
    public boolean existsByName(String name) {
        return roleJpa.existsByNameAndEnabledTrue(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return roleJpa.existsByNameAndIdNotAndEnabledTrue(name, id);
    }

    @Override
    public long countNative(){
        return roleJpa.count();
    }
}