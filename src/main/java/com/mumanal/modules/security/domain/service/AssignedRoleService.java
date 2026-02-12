package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.response.AssignmentResponse;

import java.util.List;

public interface AssignedRoleService {
    List<AssignmentResponse> getAll();
    List<AssignmentResponse> getAllByUser(Integer userId);
    AssignmentResponse getById(Integer id);
    AssignmentResponse create(CreateAssignmentRequest request);
    AssignmentResponse update(Integer id, UpdateAssignmentRequest request);
    void delete(Integer id);
}