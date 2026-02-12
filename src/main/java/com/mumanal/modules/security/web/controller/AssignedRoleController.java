package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.dto.request.CreateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.response.AssignmentResponse;
import com.mumanal.modules.security.domain.service.AssignedRoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/assignments")
public class AssignedRoleController {

    private final AssignedRoleService assignedRoleService;

    public AssignedRoleController(AssignedRoleService assignedRoleService) {
        this.assignedRoleService = assignedRoleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ASSIGNMENT_READ') or hasRole('ROOT')")
    public ResponseEntity<List<AssignmentResponse>> getAll(
            @RequestParam(required = false) Integer userId) {

        if (userId != null) {
            return ResponseEntity.ok(assignedRoleService.getAllByUser(userId));
        }
        return ResponseEntity.ok(assignedRoleService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ASSIGNMENT_READ') or hasRole('ROOT')")
    public ResponseEntity<AssignmentResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(assignedRoleService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ASSIGNMENT_CREATE') or hasRole('ROOT')")
    public ResponseEntity<AssignmentResponse> create(@RequestBody @Valid CreateAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignedRoleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ASSIGNMENT_UPDATE') or hasRole('ROOT')")
    public ResponseEntity<AssignmentResponse> update(@PathVariable Integer id,
                                                     @RequestBody @Valid UpdateAssignmentRequest request) {
        return ResponseEntity.ok(assignedRoleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ASSIGNMENT_DELETE') or hasRole('ROOT')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        assignedRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}