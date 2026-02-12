package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateMenuRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuResponse;
import com.mumanal.modules.security.domain.exception.NotBeASameFatherException;
import com.mumanal.modules.security.domain.repository.MenuRepository;
import com.mumanal.modules.security.persistence.entity.SecMenuEntity;
import com.mumanal.modules.security.persistence.entity.SecPermissionEntity;
import com.mumanal.modules.security.persistence.mapper.MenuMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyActiveException;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final String RESOURCE_NAME = "Menu";

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public List<MenuResponse> getAll(Boolean status) {
        List<SecMenuEntity> entities;
        if (Boolean.TRUE.equals(status)) {
            entities = menuRepository.findAllActive();
        } else {
            entities = menuRepository.findAll();
        }
        return menuMapper.toDto(entities);
    }

    @Override
    public MenuResponse getById(Integer id) {
        SecMenuEntity entity = findEntityById(id);
        return menuMapper.toDto(entity);
    }

    @Override
    @Transactional
    public MenuResponse create(CreateMenuRequest request) {
        // 1. Validar unicidad de nombre bajo el mismo padre
        if (menuRepository.existsByNameAndParentId(request.name(), request.parentMenuId())) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", request.name());
        }

        SecMenuEntity entity = menuMapper.toEntity(request);

        // 2. Asignar Padre si existe
        if (request.parentMenuId() != null) {
            SecMenuEntity parent = findEntityById(request.parentMenuId());
            entity.setParentMenu(parent);
        }

        return menuMapper.toDto(menuRepository.save(entity));
    }

    @Override
    @Transactional
    public MenuResponse update(Integer id, UpdateMenuRequest request) {
        SecMenuEntity menu = findEntityById(id);

        // 1. Validar Referencia Circular (No puedo ser mi propio padre)
        if (request.parentMenuId() != null && request.parentMenuId().equals(id)) {
            throw new NotBeASameFatherException();
        }

        // 2. Gestionar cambio de Padre
        if (request.parentMenuId() != null) {
            // Si viene un ID, buscamos el padre
            if (menu.getParentMenu() == null || !menu.getParentMenu().getId().equals(request.parentMenuId())) {
                SecMenuEntity newParent = findEntityById(request.parentMenuId());
                menu.setParentMenu(newParent);
            }
        } else {
            // Si viene null, lo convertimos en raíz
            menu.setParentMenu(null);
        }

        // 3. Validar duplicados (excluyendo el actual)
        if (menuRepository.existsByNameAndParentIdAndIdNot(request.name(), request.parentMenuId(), id)) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", request.name());
        }

        menuMapper.updateEntityFromDto(request, menu);
        return menuMapper.toDto(menuRepository.save(menu));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        SecMenuEntity entity = findEntityById(id);

        // Opcional: Validar si tiene hijos activos antes de eliminar
        // if (!entity.getSubMenus().isEmpty()) throw new BusinessRuleException("Cannot delete menu with children");

        entity.softDelete();
        menuRepository.save(entity);
    }

    @Override
    @Transactional
    public void recover(Integer id) {
        SecMenuEntity entity = menuRepository.findByIdNative(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));

        Integer parentId = entity.getParentMenu() != null ? entity.getParentMenu().getId() : null;

        if (menuRepository.existsByNameAndParentIdAndIdNot(entity.getName(), parentId, id)) {
            throw new ResourceAlreadyExistsException(RESOURCE_NAME, "name", entity.getName());
        }

        if (Boolean.TRUE.equals(entity.getEnabled())) {
            throw new ResourceAlreadyActiveException(RESOURCE_NAME, id);
        }

        entity.recover();
        menuRepository.save(entity);
    }

    private SecMenuEntity findEntityById(Integer id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id.toString()));
    }

    @Override
    public List<MenuResponse> getTreeForCurrentUser() {
        // 1. Obtener permisos del usuario actual (extraídos del JWT por tu filtro)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> userPermissions = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 2. Traer TODOS los menús activos (Planos)
        // Es mejor traer todo y armar el árbol en Java para evitar N+1 queries
        List<SecMenuEntity> allMenus = menuRepository.findAllActive(); // Implementa findAllActiveAndEnabled ordenado por sortOrder

        // 3. Filtrar y construir árbol
        List<SecMenuEntity> rootMenus = allMenus.stream()
                // Solo nos interesan los padres (Roots) para empezar
                .filter(menu -> menu.getParentMenu() == null)
                // Que el usuario tenga permiso de ver este ROOT
                .filter(menu -> canUserSeeMenu(menu, userPermissions))
                .toList();

        // 4. Convertir a DTO (El mapper se encargará de los hijos, pero debemos filtrarlos también)
        // Nota: Como MapStruct mapea todo "subMenus", necesitamos una estrategia.
        // Lo más limpio profesionalmente es hacerlo manual o usar un "AfterMapping" en MapStruct.
        // Para simplificar y ser didácticos, lo haré manual aquí:

        return rootMenus.stream()
                .map(menu -> mapMenuWithFiltering(menu, userPermissions))
                .toList();
    }

    // Método recursivo manual para filtrar hijos en profundidad
    private MenuResponse mapMenuWithFiltering(SecMenuEntity menu, List<String> userPermissions) {
        // Filtramos los hijos de este menú
        List<MenuResponse> childrenDto = new ArrayList<>();

        if (menu.getSubMenus() != null) {
            childrenDto = menu.getSubMenus().stream()
                    .filter(subMenu -> subMenu.getEnabled() && Boolean.TRUE.equals(subMenu.getStatus())) // Solo activos
                    .filter(subMenu -> canUserSeeMenu(subMenu, userPermissions)) // Solo permitidos
                    .map(subMenu -> mapMenuWithFiltering(subMenu, userPermissions)) // Recursividad
                    .toList();
        }

        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getRoute(),
                menu.getIcon(),
                childrenDto // Lista filtrada
        );
    }

    private boolean canUserSeeMenu(SecMenuEntity menu, List<String> userPermissions) {
        // Si es público, todos lo ven
        if (Boolean.TRUE.equals(menu.getIsPublic())) {
            return true;
        }

        // Si el menú NO tiene permisos asignados en la tabla intermedia,
        // decidimos: ¿Se ve o se oculta?
        // Política segura: Si no requiere permisos explícitos, solo ROOT lo ve
        // O Política abierta: Si no tiene requisitos, es público.
        // Asumiremos: Si tiene permisos requeridos, el usuario debe tener AL MENOS UNO.

        if (menu.getRequiredPermissions() == null || menu.getRequiredPermissions().isEmpty()) {
            return true; // O false, depende de tu política "Default Deny"
        }

        // Verificamos si el usuario tiene ALGUNO de los permisos requeridos por el menú
        for (SecPermissionEntity requiredPerm : menu.getRequiredPermissions()) {
            if (userPermissions.contains(requiredPerm.getCode())) {
                return true;
            }
        }

        return false;
    }
}