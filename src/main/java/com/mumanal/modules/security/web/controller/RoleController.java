package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.dto.request.CreateRoleRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateRoleRequest;
import com.mumanal.modules.security.domain.dto.response.RoleResponse;
import com.mumanal.modules.security.domain.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_READ')")
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_READ')")
    public ResponseEntity<RoleResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid CreateRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> update(@PathVariable Integer id,
                                               @RequestBody @Valid UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/recover")
    @PreAuthorize("hasRole('ROOT') or hasAuthority('ROLE_RECOVER')")
    public ResponseEntity<Void> recover(@PathVariable Integer id) {
        roleService.recover(id);
        return ResponseEntity.ok().build();
    }
}