// infrastructure/adapters/output/persistence/adapter/TokenRefreshRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.domain.ports.output.TokenRefreshRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.TokenRefreshMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaTokenRefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRefreshRepositoryAdapter implements TokenRefreshRepositoryPort {
    
    private final JpaTokenRefreshRepository jpaRepository;
    private final TokenRefreshMapper mapper;
    
    @Override
    public TokenRefresh save(TokenRefresh tokenRefresh) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(tokenRefresh)));
    }
    
    @Override
    public Optional<TokenRefresh> findByToken(String token) {
        return jpaRepository.findByToken(token).map(mapper::toDomain);
    }
    
    @Override
    @Transactional
    public void deleteByEntidadAndEntidadId(String entidad, Long entidadId) {
        jpaRepository.deleteByEntidadAndEntidadId(entidad, entidadId);
    }
    
    @Override
    @Transactional
    public void revokeAllByEntidad(String entidad, Long entidadId) {
        jpaRepository.revokeAllByEntidadAndEntidadId(entidad, entidadId);
    }
}