package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.response.VoucherResponse;

import java.util.List;

public interface VoucherService {
    List<VoucherResponse> getAll();
    VoucherResponse getById(Integer id);
    VoucherResponse create(CreateVoucherRequest request);
    VoucherResponse update(Integer id, UpdateVoucherRequest request);
    void delete(Integer id);
}