package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.AssignPermissionToMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuPermissionResponse;
import com.mumanal.modules.security.domain.repository.MenuPermissionRepository;
import com.mumanal.modules.security.domain.repository.MenuRepository;
import com.mumanal.modules.security.domain.repository.PermissionRepository;
import com.mumanal.modules.security.persistence.entity.SecMenuEntity;
import com.mumanal.modules.security.persistence.entity.SecMenuPermissionEntity;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import com.mumanal.modules.security.persistence.mapper.MenuPermissionMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyActiveException;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuPermissionServiceImpl implements MenuPermissionService {

    private final MenuPermissionRepository menuPermissionRepository;
    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository; // Necesitas inyectar esto (o PermissionJpa)
    private final MenuPermissionMapper mapper;
    private final String RESOURCE_NAME = "MENU";

    public MenuPermissionServiceImpl(MenuPermissionRepository menuPermissionRepository,
                                     MenuRepository menuRepository,
                                     PermissionRepository permissionRepository,
                                     MenuPermissionMapper mapper) {
        this.menuPermissionRepository = menuPermissionRepository;
        this.menuRepository = menuRepository;
        this.permissionRepository = permissionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MenuPermissionResponse> getPermissionsByMenu(Integer menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu", "id", menuId.toString());
        }
        return mapper.toDto(menuPermissionRepository.findAllByMenuId(menuId));
    }

    @Override
    @Transactional
    public List<MenuPermissionResponse> assignPermissions(AssignPermissionToMenuRequest request) {
        SecMenuEntity menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", request.menuId().toString()));

        List<SecMenuPermissionEntity> assigned = new ArrayList<>();

        for (Integer permId : request.permissionIds()) {
            // 1. Validar que el permiso exista
            SecPermissionEntity permission = permissionRepository.findById(permId) // Asegúrate que tu Repo de permisos tenga findById
                    .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permId.toString()));

            // 2. Evitar duplicados (Idempotencia)
            if (menuPermissionRepository.existsByMenuIdAndPermissionId(menu.getId(), permId)) {
                continue; // Ya existe, saltamos
            }

            // 3. Crear relación
            SecMenuPermissionEntity entity = new SecMenuPermissionEntity();
            entity.setMenu(menu);
            entity.setPermission(permission);
            // Auditoría se llena automáticamente por AuditorAware

            assigned.add(menuPermissionRepository.save(entity));
        }

        return mapper.toDto(assigned);
    }

    @Override
    @Transactional
    public void revokePermission(Integer menuId, Integer permissionId) {
        if (!menuRepository.existsById(menuId)) {
            throw new ResourceNotFoundException("Menu", "id", menuId.toString());
        }
        // No validamos si existe el permiso ID para ahorrar query, si no existe el delete no hace nada
        menuPermissionRepository.deleteByMenuAndPermission(menuId, permissionId);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 1. Buscar entidad (Solo si está activa/enabled)
        SecMenuEntity entity = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        // 2. Soft Delete
        entity.softDelete(); // Método de AuditableEntity

        // 3. Guardar cambios vía Repository
        menuRepository.save(entity);
    }

    @Override
    @Transactional
    public void recover(Integer id) {
        // 1. Buscar Nativo (Para encontrarlo aunque esté enabled=false)
        SecMenuEntity entity = menuRepository.findByIdNative(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        // 2. Validar que no esté activo ya
        if (Boolean.TRUE.equals(entity.getEnabled())) {
            throw new ResourceAlreadyActiveException(RESOURCE_NAME, id);
        }

        // 3. Validar consistencia del Padre (Lógica específica de Menús)
        // Si tiene un padre asignado, ese padre NO debe estar eliminado.
        if (entity.getParentMenu() != null) {
            // Buscamos al padre para ver su estado actual
            SecMenuEntity parent = menuRepository.findById(entity.getParentMenu().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Menu", "id", entity.getParentMenu().getId().toString()));
            // Nota: findById lanza excepción si el padre está soft-deleted, así que esta validación es implícita.
        }

        // 4. Validar Duplicados al recuperar
        // Si recupero este menú, ¿chocará el nombre con otro menú activo en el mismo nivel?
        Integer parentId = entity.getParentMenu() != null ? entity.getParentMenu().getId() : null;
        if (menuRepository.existsByNameAndParentIdAndIdNot(entity.getName(), parentId, id)) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", entity.getName());
        }

        // 5. Recuperar y Guardar
        entity.recover(); // Método de AuditableEntity
        menuRepository.save(entity);
    }
}