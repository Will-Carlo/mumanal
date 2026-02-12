package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.UserRepository;
import com.mumanal.modules.security.persistence.entity.SecUserEntity;
import com.mumanal.modules.security.persistence.repository.jpa.UserJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpa userJpa;

    public UserRepositoryImpl(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public Optional<SecUserEntity> findByUsername(String username) {
        return userJpa.findByUsernameAndEnabledTrue(username);
    }

    @Override
    public List<SecUserEntity> findAll() {
        return userJpa.findAllByEnabledTrue();
    }

    @Override
    public List<SecUserEntity> findAllExcludingRoot() {
        return userJpa.findAllExcludingRoot();
    }

    @Override
    public Optional<SecUserEntity> findById(Integer id) {
        return userJpa.findByIdAndEnabledTrue(id);
    }

    @Override
    public Optional<SecUserEntity> findByIdNative(Integer id) {
        return userJpa.findById(id);
    }

    @Override
    public SecUserEntity save(SecUserEntity entity) {
        return userJpa.save(entity);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpa.existsByUsernameAndEnabledTrue(username);
    }
}
