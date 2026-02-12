package com.mumanal.modules.generic.web.controller;

import com.mumanal.modules.generic.domain.dto.request.CreateCityRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdateCityRequest;
import com.mumanal.modules.generic.domain.dto.response.CityResponse;
import com.mumanal.modules.generic.domain.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generic/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CityResponse> create(@RequestBody @Valid CreateCityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cityService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> update(@PathVariable Integer id,
                                               @RequestBody @Valid UpdateCityRequest request) {
        return ResponseEntity.ok(cityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}