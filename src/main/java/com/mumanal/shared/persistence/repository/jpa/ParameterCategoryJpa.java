package com.mumanal.shared.persistence.repository.jpa;

import com.mumanal.shared.persistence.entity.GenParameterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParameterCategoryJpa extends JpaRepository<GenParameterCategoryEntity, Integer> {
    boolean existsByCodeAndEnabledTrue(String code);
    Optional<GenParameterCategoryEntity> findByCodeAndEnabledTrue(String code);
}
