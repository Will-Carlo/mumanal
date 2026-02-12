package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.constant.AppPermissions;
import com.mumanal.modules.security.domain.dto.request.AssignPermissionToMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuPermissionResponse;
import com.mumanal.modules.security.domain.service.MenuPermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/menus")
public class MenuPermissionController {

    private final MenuPermissionService service;

    public MenuPermissionController(MenuPermissionService service) {
        this.service = service;
    }

    /**
     * Listar permisos asignados a un menú
     * GET /menus/{menuId}/permissions
     */
    @GetMapping("/{menuId}/permissions")
    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<List<MenuPermissionResponse>> getPermissions(@PathVariable Integer menuId) {
        return ResponseEntity.ok(service.getPermissionsByMenu(menuId));
    }

    /**
     * Asignar uno o más permisos a un menú
     * POST /menus/permissions
     * Body: { "menuId": 1, "permissionIds": [5, 6] }
     */
    @PostMapping("/permissions")
    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<List<MenuPermissionResponse>> assignPermissions(
            @RequestBody @Valid AssignPermissionToMenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.assignPermissions(request));
    }

    /**
     * Revocar un permiso específico de un menú
     * DELETE /menus/{menuId}/permissions/{permissionId}
     */
    @DeleteMapping("/{menuId}/permissions/{permissionId}")
    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<Void> revokePermission(
            @PathVariable Integer menuId,
            @PathVariable Integer permissionId) {
        service.revokePermission(menuId, permissionId);
        return ResponseEntity.noContent().build();
    }
}