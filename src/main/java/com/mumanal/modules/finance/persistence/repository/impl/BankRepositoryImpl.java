package com.mumanal.modules.finance.persistence.repository.impl;

import com.mumanal.modules.finance.domain.repository.BankRepository;
import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import com.mumanal.modules.finance.persistence.repository.jpa.BankJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BankRepositoryImpl implements BankRepository {

    private final BankJpa bankJpa;

    public BankRepositoryImpl(BankJpa bankJpa) {
        this.bankJpa = bankJpa;
    }

    @Override
    public List<FinBankEntity> findAll() {
        return bankJpa.findAllByEnabledTrue();
    }

    @Override
    public Optional<FinBankEntity> findById(Integer id) {
        return bankJpa.findByIdAndEnabledTrue(id);
    }

    @Override
    public FinBankEntity save(FinBankEntity entity) {
        return bankJpa.save(entity);
    }

    @Override
    public boolean existsByName(String name) {
        return bankJpa.existsByNameAndEnabledTrue(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return bankJpa.existsByNameAndIdNotAndEnabledTrue(name, id);
    }

    @Override
    public boolean existsByBankCode(String bankCode) {
        return bankJpa.existsByBankCodeAndEnabledTrue(bankCode);
    }

    @Override
    public boolean existsByBankCodeAndIdNot(String bankCode, Integer id) {
        return bankJpa.existsByBankCodeAndIdNotAndEnabledTrue(bankCode, id);
    }
}