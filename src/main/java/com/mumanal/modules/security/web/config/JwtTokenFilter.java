package com.mumanal.modules.security.web.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mumanal.modules.security.domain.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Tu UserServiceImpl

    public JwtTokenFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Extract the Header Authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Get the clean token
        String token = authHeader.substring(7); // "Bearer "

        // 3. Validate Token
        DecodedJWT decodedJWT = jwtService.validateToken(token);

        // If the token is valid and there is no authentication in the current context
        if (decodedJWT != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtService.extractUsername(decodedJWT);

            // 4. Load User from the Database (This validates that it exists and is active)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Create Authentication Object
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities() // Roles and Permissions (loaded in UserServiceImpl)
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. Establish in context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continue chain
        filterChain.doFilter(request, response);
    }
}