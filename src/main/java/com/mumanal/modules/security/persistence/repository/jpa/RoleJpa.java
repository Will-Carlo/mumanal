package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecRoleEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleJpa extends ListCrudRepository<SecRoleEntity, Integer> {
    List<SecRoleEntity> findAllByEnabledTrue();
    Optional<SecRoleEntity> findByIdAndEnabledTrue(Integer id);
    boolean existsByNameAndEnabledTrue(String name);
    boolean existsByNameAndIdNotAndEnabledTrue(String name, Integer id);
    long countAllByEnabledTrue();
}