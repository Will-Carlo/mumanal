package com.mumanal.modules.finance.domain.repository;

import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import java.util.List;
import java.util.Optional;

public interface BankRepository {
    List<FinBankEntity> findAll();
    Optional<FinBankEntity> findById(Integer id);
    FinBankEntity save(FinBankEntity entity);

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByBankCode(String bankCode);
    boolean existsByBankCodeAndIdNot(String bankCode, Integer id);
}