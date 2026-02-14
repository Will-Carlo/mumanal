package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.response.AffiliateResponse;

import java.util.List;

public interface AffiliateService {
    List<AffiliateResponse> getAll();
    AffiliateResponse getById(Integer id);
    AffiliateResponse create(CreateAffiliateRequest request);
    AffiliateResponse update(Integer id, UpdateAffiliateRequest request);
    void delete(Integer id);
}