// application/service/auth/RefreshTokenService.java
package com.policlinico.smartsalud.application.service.auth;

import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.shared.exception.TokenRefreshException;

public interface RefreshTokenService {
    TokenRefresh createRefreshToken(Long userId, String entidad);
    TokenRefresh verifyExpiration(TokenRefresh token);
    void deleteByUserId(Long userId, String entidad);
    TokenRefresh findByToken(String token);
}