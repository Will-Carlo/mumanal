package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateBankRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateBankRequest;
import com.mumanal.modules.finance.domain.dto.response.BankResponse;
import com.mumanal.modules.finance.domain.repository.BankRepository;
import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import com.mumanal.modules.finance.persistence.mapper.BankMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    private final String RESOURCE = "Bank";

    public BankServiceImpl(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    @Override
    public List<BankResponse> getAll() {
        return bankMapper.toDto(bankRepository.findAll());
    }

    @Override
    public BankResponse getById(Integer id) {
        FinBankEntity entity = bankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        return bankMapper.toDto(entity);
    }

    @Override
    @Transactional
    public BankResponse create(CreateBankRequest request) {
        // Validar nombre único
        if (bankRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "name", request.name());
        }
        // Validar código único
        if (bankRepository.existsByBankCode(request.bankCode())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "bankCode", request.bankCode());
        }

        FinBankEntity entity = bankMapper.toEntity(request);
        return bankMapper.toDto(bankRepository.save(entity));
    }

    @Override
    @Transactional
    public BankResponse update(Integer id, UpdateBankRequest request) {
        FinBankEntity entity = bankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        // Validar unicidad de nombre al actualizar (si cambió)
        if (request.name() != null && !request.name().equals(entity.getName())) {
            if (bankRepository.existsByNameAndIdNot(request.name(), id)) {
                throw new ResourceAlreadyExistsException(RESOURCE, "name", request.name());
            }
        }

        // Validar unicidad de código al actualizar (si cambió)
        if (request.bankCode() != null && !request.bankCode().equals(entity.getBankCode())) {
            if (bankRepository.existsByBankCodeAndIdNot(request.bankCode(), id)) {
                throw new ResourceAlreadyExistsException(RESOURCE, "bankCode", request.bankCode());
            }
        }

        bankMapper.updateEntityFromDto(request, entity);
        return bankMapper.toDto(bankRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        FinBankEntity entity = bankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        // Aquí podrías agregar validación extra: No borrar si tiene Vouchers asociados
        // if (!entity.getVouchers().isEmpty()) { throw ... }

        entity.softDelete();
        bankRepository.save(entity);
    }
}