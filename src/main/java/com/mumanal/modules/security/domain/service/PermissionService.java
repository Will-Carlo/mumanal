package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreatePermissionRequest;
import com.mumanal.modules.security.domain.dto.request.UpdatePermissionRequest;
import com.mumanal.modules.security.domain.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> getAll();
    PermissionResponse getById(Integer id);
    PermissionResponse create(CreatePermissionRequest request);
    PermissionResponse update(Integer id, UpdatePermissionRequest request);
    void delete(Integer id);
}
