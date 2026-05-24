// infrastructure/config/security/JwtTokenProvider.java
package com.policlinico.smartsalud.infrastructure.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    public String generateAccessToken(UsuarioPrincipal userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .claim("email", userPrincipal.getEmail())
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return Long.parseLong(claims.getSubject());
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("JWT malformado: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("JWT no soportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT vacío: {}", ex.getMessage());
        }
        return false;
    }
    
    public long getAccessTokenExpirationMs() {
        return jwtExpirationMs;
    }
}