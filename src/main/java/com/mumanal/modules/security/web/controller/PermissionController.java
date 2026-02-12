package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.constant.AppPermissions;
import com.mumanal.modules.security.domain.dto.request.CreatePermissionRequest;
import com.mumanal.modules.security.domain.dto.request.UpdatePermissionRequest;
import com.mumanal.modules.security.domain.dto.response.PermissionResponse;
import com.mumanal.modules.security.domain.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @PreAuthorize(AppPermissions.SEC_ROLES_MANAGE)
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize(AppPermissions.SEC_ROLES_MANAGE)
    public ResponseEntity<PermissionResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @PostMapping
    @PreAuthorize(AppPermissions.SEC_ROLES_MANAGE)
    public ResponseEntity<PermissionResponse> create(@RequestBody @Valid CreatePermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AppPermissions.SEC_ROLES_MANAGE)
    public ResponseEntity<PermissionResponse> update(@PathVariable Integer id,
                                                     @RequestBody @Valid UpdatePermissionRequest request) {
        return ResponseEntity.ok(permissionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AppPermissions.SEC_ROLES_MANAGE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}