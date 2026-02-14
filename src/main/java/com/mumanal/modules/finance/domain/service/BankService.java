package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateBankRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateBankRequest;
import com.mumanal.modules.finance.domain.dto.response.BankResponse;

import java.util.List;

public interface BankService {
    List<BankResponse> getAll();
    BankResponse getById(Integer id);
    BankResponse create(CreateBankRequest request);
    BankResponse update(Integer id, UpdateBankRequest request);
    void delete(Integer id);
}