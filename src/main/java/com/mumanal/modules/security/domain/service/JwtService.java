package com.mumanal.modules.security.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mumanal.modules.security.domain.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${app.security.jwt.secret}")
    private String secretKey;

    @Value("${app.security.jwt.expiration}")
    private long expirationTime;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    public String createToken(UserDetails user) {
        Integer companyId = null;

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer("mumanal-api")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withJWTId(UUID.randomUUID().toString()) // JTI para evitar replay attacks
                // Opcional: Puedes agregar roles aquí para que el frontend los lea sin ir a la BD
                // .withClaim("roles", List.of("ADMIN", "SELLER"))
                .withClaim("authorities", authorities)
                .withClaim("companyId", companyId)
                .sign(getAlgorithm());
    }

    /**
     * Validate and return the DecodedJWT if it is correct.
     * Returns null if it fails (expired or invalid signature).
     */
    public DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm())
                    .withIssuer("mumanal-api")
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            // Log the error if necessary (expired token, bad signature, etc.)
            return null;
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }
}