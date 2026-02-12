package com.mumanal.modules.generic.domain.service;

import com.mumanal.modules.generic.domain.dto.request.CreateCityRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdateCityRequest;
import com.mumanal.modules.generic.domain.dto.response.CityResponse;

import java.util.List;

public interface CityService {
    List<CityResponse> getAll();
    CityResponse getById(Integer id);
    CityResponse create(CreateCityRequest request);
    CityResponse update(Integer id, UpdateCityRequest request);
    void delete(Integer id);
}