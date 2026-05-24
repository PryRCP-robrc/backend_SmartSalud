// application/ports/input/auth/RefreshTokenInputPort.java
package com.policlinico.smartsalud.application.ports.input.auth;

import com.policlinico.smartsalud.domain.model.TokenRefresh;

public interface RefreshTokenInputPort {
    
    TokenRefresh createRefreshToken(Long entidadId, String entidad);
    
    TokenRefresh verifyExpiration(TokenRefresh token);
    
    void deleteByEntidadId(Long entidadId, String entidad);
    
    TokenRefresh findByToken(String token);
}