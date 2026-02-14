package com.mumanal.modules.finance.persistence.repository.impl;

import com.mumanal.modules.finance.domain.repository.VoucherRepository;
import com.mumanal.modules.finance.persistence.entity.FinVoucherEntity;
import com.mumanal.modules.finance.persistence.repository.jpa.VoucherJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {
    private final VoucherJpa voucherJpa;

    public VoucherRepositoryImpl(VoucherJpa voucherJpa) {
        this.voucherJpa = voucherJpa;
    }

    @Override
    public List<FinVoucherEntity> findAll() { return voucherJpa.findAllByEnabledTrue(); }

    @Override
    public Optional<FinVoucherEntity> findById(Integer id) { return voucherJpa.findByIdAndEnabledTrue(id); }

    @Override
    public FinVoucherEntity save(FinVoucherEntity entity) { return voucherJpa.save(entity); }

    @Override
    public boolean existsByDepositNumberAndBank(Integer depositNumber, Integer bankId) {
        return voucherJpa.existsByDepositNumberAndBankIdAndEnabledTrue(depositNumber, bankId);
    }
}