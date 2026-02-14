package com.mumanal.modules.finance.persistence.repository.jpa;

import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface BankJpa extends ListCrudRepository<FinBankEntity, Integer> {

    // Métodos que respetan el Soft Delete (AuditableEntity)
    List<FinBankEntity> findAllByEnabledTrue();
    Optional<FinBankEntity> findByIdAndEnabledTrue(Integer id);

    // Validaciones de negocio
    boolean existsByNameAndEnabledTrue(String name);
    boolean existsByNameAndIdNotAndEnabledTrue(String name, Integer id);

    boolean existsByBankCodeAndEnabledTrue(String bankCode);
    boolean existsByBankCodeAndIdNotAndEnabledTrue(String bankCode, Integer id);
}