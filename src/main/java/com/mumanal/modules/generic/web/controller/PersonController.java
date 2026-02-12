package com.mumanal.modules.generic.web.controller;

import com.mumanal.modules.generic.domain.dto.request.CreatePersonRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdatePersonRequest;
import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import com.mumanal.modules.generic.domain.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generic/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@RequestBody @Valid CreatePersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Integer id,
                                                 @RequestBody @Valid UpdatePersonRequest request) {
        return ResponseEntity.ok(personService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}