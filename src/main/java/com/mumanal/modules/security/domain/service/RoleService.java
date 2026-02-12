package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateRoleRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateRoleRequest;
import com.mumanal.modules.security.domain.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAll();
    RoleResponse getById(Integer id);
    RoleResponse create(CreateRoleRequest request);
    RoleResponse update(Integer id, UpdateRoleRequest request);
    void delete(Integer id);
    void recover(Integer id);
}
