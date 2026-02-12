package com.mumanal.modules.generic.domain.repository;

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<GenPersonEntity> findAll();
    Optional<GenPersonEntity> findById(Integer id);
    GenPersonEntity save(GenPersonEntity entity);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    Optional<GenPersonEntity> findByIdentityCard(String identityCard);
}
