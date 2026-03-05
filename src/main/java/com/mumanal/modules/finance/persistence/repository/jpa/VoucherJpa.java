package com.mumanal.modules.finance.persistence.repository.jpa;

import com.mumanal.modules.finance.persistence.entity.FinVoucherEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherJpa extends ListCrudRepository<FinVoucherEntity, Integer> {
    List<FinVoucherEntity> findAllByEnabledTrue();
    Optional<FinVoucherEntity> findByIdAndEnabledTrue(Integer id);

    // Ejemplo de búsqueda útil: verificar duplicados de depósito
    boolean existsByDepositNumberAndBankIdAndEnabledTrue(String depositNumber, Integer bankId);
}