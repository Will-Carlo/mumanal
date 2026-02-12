package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.MenuRepository;
import com.mumanal.modules.security.persistence.entity.SecMenuEntity;
import com.mumanal.modules.security.persistence.repository.jpa.MenuJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MenuRepositoryImpl implements MenuRepository {
    private final MenuJpa secMenuJpa;

    public MenuRepositoryImpl(MenuJpa secMenuJpa) {
        this.secMenuJpa = secMenuJpa;
    }

    @Override
    public List<SecMenuEntity> findAll() {
        return secMenuJpa.findAllEnabled();
    }

    @Override
    public List<SecMenuEntity> findAllActive() {
        return secMenuJpa.findAllActiveAndEnabled();
    }

    @Override
    public Optional<SecMenuEntity> findById(Integer id) {
        return secMenuJpa.findByIdAndEnabledTrue(id);
    }

    @Override
    public Optional<SecMenuEntity> findByIdNative(Integer id) {
        return secMenuJpa.findById(id);
    }

    @Override
    public SecMenuEntity save(SecMenuEntity entity) {
        return secMenuJpa.save(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return secMenuJpa.existsByIdAndEnabledTrue(id);
    }

    @Override
    public boolean existsByNameAndParentId(String name, Integer parentId) {
        return secMenuJpa.existsByNameAndParentId(name, parentId);
    }

    @Override
    public boolean existsByNameAndParentIdAndIdNot(String name, Integer parentId, Integer id) {
        return secMenuJpa.existsByNameAndParentIdAndIdNot(name, parentId, id);
    }
}