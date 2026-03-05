package com.mumanal.modules.finance.domain.repository;

import com.mumanal.modules.finance.persistence.entity.FinVoucherEntity;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    List<FinVoucherEntity> findAll();
    Optional<FinVoucherEntity> findById(Integer id);
    FinVoucherEntity save(FinVoucherEntity entity);
    boolean existsByDepositNumberAndBank(String depositNumber, Integer bankId);
}