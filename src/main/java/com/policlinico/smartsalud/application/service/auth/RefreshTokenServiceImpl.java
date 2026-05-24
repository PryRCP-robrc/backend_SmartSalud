// application/service/auth/RefreshTokenServiceImpl.java
package com.policlinico.smartsalud.application.service.auth;

import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.domain.model.enums.EstadoToken;
import com.policlinico.smartsalud.domain.ports.output.TokenRefreshRepositoryPort;
import com.policlinico.smartsalud.shared.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    
    private final TokenRefreshRepositoryPort tokenRefreshRepository;
    
    @Value("${app.jwt.refresh-expiration-ms}")
    private Long refreshTokenDurationMs;
    
    @Override
    public TokenRefresh createRefreshToken(Long entidadId, String entidad) {
        // Revocar tokens anteriores
        tokenRefreshRepository.revokeAllByEntidad(entidad, entidadId);
        
        TokenRefresh tokenRefresh = TokenRefresh.builder()
                .entidad(entidad)
                .entidadId(entidadId)
                .token(UUID.randomUUID().toString())
                .expiraEn(LocalDateTime.now().plusSeconds(refreshTokenDurationMs / 1000))
                .estado(EstadoToken.ACTIVO)
                .creadoEn(LocalDateTime.now())
                .build();
        
        return tokenRefreshRepository.save(tokenRefresh);
    }
    
    @Override
    public TokenRefresh verifyExpiration(TokenRefresh token) {
        if (token.isExpirado()) {
            token.setEstado(EstadoToken.EXPIRADO);
            tokenRefreshRepository.save(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token expirado. Por favor, inicie sesión nuevamente.");
        }
        return token;
    }
    
    @Override
    @Transactional
    public void deleteByUserId(Long entidadId, String entidad) {
        tokenRefreshRepository.deleteByEntidadAndEntidadId(entidad, entidadId);
    }
    
    @Override
    public TokenRefresh findByToken(String token) {
        return tokenRefreshRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException(token, "Refresh token no encontrado"));
    }
}