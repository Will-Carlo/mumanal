package com.mumanal.modules.generic.persistence.repository.jpa; // Nota el paquete shared

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonJpa extends ListCrudRepository<GenPersonEntity, Integer> {
    List<GenPersonEntity> findAllByEnabledTrue();
    Optional<GenPersonEntity> findByIdAndEnabledTrue(Integer id);

    boolean existsByEmailAndEnabledTrue(String email);
    boolean existsByEmailAndIdNotAndEnabledTrue(String email, Integer id);
    Optional<GenPersonEntity> findByIdentityCardAndEnabledTrue(String identityCard);
    boolean existsByIdentityCard(String identityCard);
}