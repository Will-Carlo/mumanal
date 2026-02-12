package com.mumanal.modules.security.domain.repository;

import com.mumanal.modules.security.persistence.entity.SecUserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<SecUserEntity> findByUsername(String username);
    List<SecUserEntity> findAll();
    List<SecUserEntity> findAllExcludingRoot();
    Optional<SecUserEntity> findById(Integer id);
    Optional<SecUserEntity> findByIdNative(Integer id);
    SecUserEntity save(SecUserEntity entity);
    boolean existsByUsername(String username);
}
