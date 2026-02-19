package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.constant.AppPermissions;
import com.mumanal.modules.security.domain.dto.request.CreateUserRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateUserRequest;
import com.mumanal.modules.security.domain.dto.response.UserResponse;
import com.mumanal.modules.security.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(AppPermissions.SEC_USERS_MANAGE)
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize(AppPermissions.SEC_USERS_MANAGE)
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    @PreAuthorize(AppPermissions.SEC_USERS_MANAGE)
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AppPermissions.SEC_USERS_MANAGE)
    public ResponseEntity<UserResponse> update(@PathVariable Integer id,
                                               @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }
}