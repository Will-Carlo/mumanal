package com.mumanal.shared.web.controller;

import com.mumanal.modules.security.domain.constant.AppPermissions;
import com.mumanal.shared.domain.dto.request.*;
import com.mumanal.shared.domain.dto.response.*;
import com.mumanal.shared.domain.service.SystemParameterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generic/parameters") // Ruta base unificada
public class SystemParameterController {

    private final SystemParameterService service;

    public SystemParameterController(SystemParameterService service) {
        this.service = service;
    }

    // --- MASTERS (CATEGORÍAS) ---

    @GetMapping("/categories")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<List<ParameterCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @PostMapping("/categories")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<ParameterCategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(request));
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<ParameterCategoryResponse> updateCategory(@PathVariable Integer id,
                                                                    @RequestBody @Valid UpdateCategoryRequest request) {
        return ResponseEntity.ok(service.updateCategory(id, request));
    }

    // --- DETAILS (PARÁMETROS) ---

    // Obtener parámetros por categoría (lo que usará tu Hook en React)
    // Ejemplo: GET /generic/parameters?categoryId=5
    @GetMapping("/id")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<List<ParameterResponse>> getParametersByID(@RequestParam Integer categoryId) {
        return ResponseEntity.ok(service.getParametersByCategory(categoryId));
    }

    @GetMapping("/code")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<List<ParameterResponse>> getParametersByCode(@RequestParam String categoryCode) {
        return ResponseEntity.ok(service.getParametersByCategoryCode(categoryCode));
    }

    // Alternativa: Obtener por código de categoría (Más útil para el Frontend)
    // Necesitarías un método en servicio getByCode, pero por ahora usemos ID o Code según prefieras.
    // Si usas el Seeder, el front sabe el código, pero para gestionar ABM a veces es más fácil el ID.

    @PostMapping
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<ParameterResponse> createParameter(@RequestBody @Valid CreateParameterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createParameter(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<ParameterResponse> updateParameter(@PathVariable Integer id,
                                                             @RequestBody @Valid UpdateParameterRequest request) {
        return ResponseEntity.ok(service.updateParameter(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AppPermissions.GEN_PARAM_MANAGE)
    public ResponseEntity<Void> deleteParameter(@PathVariable Integer id) {
        service.deleteParameter(id);
        return ResponseEntity.noContent().build();
    }
}