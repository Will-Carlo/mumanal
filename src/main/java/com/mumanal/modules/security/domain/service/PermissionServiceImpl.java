package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreatePermissionRequest;
import com.mumanal.modules.security.domain.dto.request.UpdatePermissionRequest;
import com.mumanal.modules.security.domain.dto.response.PermissionResponse;
import com.mumanal.modules.security.domain.repository.PermissionRepository;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import com.mumanal.modules.security.persistence.mapper.PermissionMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final String RESOURCE_NAME = "PERMISSION";

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionResponse> getAll() {
        return permissionMapper.toDto(permissionRepository.findAll());
    }

    @Override
    public PermissionResponse getById(Integer id) {
        SecPermissionEntity entity = findEntityById(id);

        return permissionMapper.toDto(entity);
    }

    @Override
    public PermissionResponse create(CreatePermissionRequest request) {
        if (permissionRepository.existsByCode(request.code())) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "code", request.code());
        }
        SecPermissionEntity entity = permissionMapper.toEntity(request);
        return permissionMapper.toDto(permissionRepository.save(entity));
    }

    @Override
    public PermissionResponse update(Integer id, UpdatePermissionRequest request) {
        SecPermissionEntity entity = findEntityById(id);

        permissionMapper.updateEntityFromDto(request, entity);
        return permissionMapper.toDto(permissionRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        SecPermissionEntity entity = findEntityById(id);

        entity.softDelete();
        permissionRepository.save(entity);
    }

    private SecPermissionEntity findEntityById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));
    }
}