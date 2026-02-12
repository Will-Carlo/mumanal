package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.dto.request.CreateMenuRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateMenuRequest;
import com.mumanal.modules.security.domain.dto.response.MenuResponse;
import com.mumanal.modules.security.domain.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Obtener todos los menús (Jerárquico o plano según tu lógica de servicio)
     */
    @GetMapping
//    @PreAuthorize(AppPermissions.SEC_MENU_READ)
    public ResponseEntity<List<MenuResponse>> getAll(
            @RequestParam(required = false) Boolean status
    ) {
        return ResponseEntity.ok(menuService.getAll(status));
    }

    /**
     * Obtener un menú por ID
     */
    @GetMapping("/{id}")
//    @PreAuthorize(AppPermissions.SEC_MENU_READ)
    public ResponseEntity<MenuResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(menuService.getById(id));
    }

    /**
     * Crear un nuevo ítem de menú
     */
    @PostMapping
//    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<MenuResponse> create(@RequestBody @Valid CreateMenuRequest request) {
        MenuResponse createdMenu = menuService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    /**
     * Actualizar un menú existente
     */
    @PutMapping("/{id}")
//    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<MenuResponse> update(@PathVariable Integer id,
                                               @RequestBody @Valid UpdateMenuRequest request) {
        return ResponseEntity.ok(menuService.update(id, request));
    }

    /**
     * Eliminar (lógicamente) un menú
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recuperar un menú eliminado
     */
    @PutMapping("/{id}/recover")
//    @PreAuthorize(AppPermissions.SEC_MENU_MANAGE)
    public ResponseEntity<Void> recover(@PathVariable Integer id) {
        menuService.recover(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtener el árbol de menús filtrado para el usuario actual
     * GET /menus/my-tree
     */
    @GetMapping("/my-tree")
//    @PreAuthorize(AppPermissions.IS_AUTHENTICATED)
    public ResponseEntity<List<MenuResponse>> getMyMenuTree() {
        return ResponseEntity.ok(menuService.getTreeForCurrentUser());
    }
}