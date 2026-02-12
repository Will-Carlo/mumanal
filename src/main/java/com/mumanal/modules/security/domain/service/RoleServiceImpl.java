package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateRoleRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateRoleRequest;
import com.mumanal.modules.security.domain.dto.response.RoleResponse;
import com.mumanal.modules.security.domain.repository.PermissionRepository;
import com.mumanal.modules.security.domain.repository.RoleRepository;
import com.mumanal.modules.security.persistence.entity.SecAssignedPermissionEntity;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import com.mumanal.modules.security.persistence.entity.SecRoleEntity;
import com.mumanal.modules.security.persistence.mapper.RoleMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyActiveException;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final String RESOURCE_NAME = "Role";

    public RoleServiceImpl(RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleMapper.toDto(roleRepository.findAll());
    }

    @Override
    public RoleResponse getById(Integer id) {
        SecRoleEntity entity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));
        return roleMapper.toDto(entity);
    }

    @Override
    @Transactional
    public RoleResponse create(CreateRoleRequest request) {
        // 1. Validar nombre único
        if (roleRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", request.name());
        }

        // 2. Crear entidad base
        SecRoleEntity role = roleMapper.toEntity(request);

        // Guardamos primero para tener ID
        role = roleRepository.save(role);

        // 3. Asignar Permisos (Tabla Intermedia)
        if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
            assignPermissionsToRole(role, request.permissionIds());
        }

        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponse update(Integer id, UpdateRoleRequest request) {
        SecRoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        // 1. Validar colisión de nombre
        if (roleRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", request.name());
        }

        // 2. Actualizar campos simples
        roleMapper.updateEntityFromDto(request, role);

        // 3. Actualizar Permisos (Estrategia: Limpiar y Re-insertar)
        if (request.permissionIds() != null) {
            // Limpiamos la lista actual. Hibernate gestionará el borrado si orphanRemoval=true está en la entidad
            // Si no, esto desconecta las referencias.
            if (role.getPermissions() != null) {
                role.getPermissions().clear();
            } else {
                role.setPermissions(new ArrayList<>());
            }

            // Agregamos los nuevos
            assignPermissionsToRole(role, request.permissionIds());
        }

        return roleMapper.toDto(roleRepository.save(role));
    }

    // Helper Privado para lógica repetitiva
    private void assignPermissionsToRole(SecRoleEntity role, List<Integer> permissionIds) {
        if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }

        for (Integer permId : permissionIds) {
            SecPermissionEntity permission = permissionRepository.findById(permId)
                    .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permId.toString()));

            // Creamos la relación
            SecAssignedPermissionEntity association = new SecAssignedPermissionEntity();
            association.setRole(role);
            association.setPermission(permission);
            // association.setGrantedBy(user...); // Auditoría extra si se requiere

            role.getPermissions().add(association);
        }
    }

    @Override
    public void delete(Integer id) {
        SecRoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        role.softDelete();
        roleRepository.save(role);
    }

    @Override
    public void recover(Integer id) {
        SecRoleEntity role = roleRepository.findByIdNative(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        if (roleRepository.existsByNameAndIdNot(role.getName(), id)) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", role.getName());
        }

        if (Boolean.TRUE.equals(role.getEnabled())) {
            throw new ResourceAlreadyActiveException(RESOURCE_NAME, id);
        }

        role.recover();
        roleRepository.save(role);
    }
}