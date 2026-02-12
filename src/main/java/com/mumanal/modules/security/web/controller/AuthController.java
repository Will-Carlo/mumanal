package com.mumanal.modules.security.web.controller;

import com.mumanal.modules.security.domain.constant.AppPermissions;
import com.mumanal.modules.security.domain.dto.request.LoginRequest;
import com.mumanal.modules.security.domain.dto.response.AuthResponse;
import com.mumanal.modules.security.domain.dto.response.UserProfileResponse;
import com.mumanal.modules.security.domain.service.JwtService;
import com.mumanal.modules.security.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.createToken(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .body(new AuthResponse(jwt));
    }

    /**
     * GET /auth/profile
     */
    @GetMapping("/profile")
    @PreAuthorize(AppPermissions.IS_AUTHENTICATED)
    public ResponseEntity<UserProfileResponse> getProfile() {
        // 1. Obtener el username del Contexto de Seguridad (Extraído del Token JWT)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Buscar datos frescos en la BD
        UserProfileResponse profile = userService.getProfileByUsername(username);

        return ResponseEntity.ok(profile);
    }
}