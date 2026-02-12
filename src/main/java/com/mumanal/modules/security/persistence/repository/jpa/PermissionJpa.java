package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionJpa extends ListCrudRepository<SecPermissionEntity, Integer> {
    List<SecPermissionEntity> findAllByEnabledTrue();
    Optional<SecPermissionEntity> findByIdAndEnabledTrue(Integer id);

    boolean existsByCode(String code);
    boolean existsByCodeAndEnabledTrue(String code);
    boolean existsByCodeAndIdNotAndEnabledTrue(String code, Integer id);
}