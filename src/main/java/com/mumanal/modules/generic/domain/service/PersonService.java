package com.mumanal.modules.generic.domain.service;

import com.mumanal.modules.generic.domain.dto.request.CreatePersonRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdatePersonRequest;
import com.mumanal.modules.generic.domain.dto.response.PersonResponse;

import java.util.List;

public interface PersonService {
    List<PersonResponse> getAll();
    PersonResponse getById(Integer id);
    PersonResponse create(CreatePersonRequest request);
    PersonResponse update(Integer id, UpdatePersonRequest request);
    void delete(Integer id);
}