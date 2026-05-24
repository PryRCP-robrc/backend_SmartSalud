// domain/ports/output/TokenRefreshRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.TokenRefresh;
import java.util.Optional;

public interface TokenRefreshRepositoryPort {
    TokenRefresh save(TokenRefresh tokenRefresh);
    Optional<TokenRefresh> findByToken(String token);
    void deleteByEntidadAndEntidadId(String entidad, Long entidadId);
    void revokeAllByEntidad(String entidad, Long entidadId);
}