// infrastructure/adapters/output/persistence/mapper/TokenRefreshMapper.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.domain.model.TokenRefresh;
import com.policlinico.smartsalud.domain.model.enums.EstadoToken;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.TokenRefreshEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenRefreshMapper {
    
    public TokenRefresh toDomain(TokenRefreshEntity entity) {
        if (entity == null) return null;
        
        EstadoToken estado = Boolean.TRUE.equals(entity.getRevocado()) ? EstadoToken.REVOCADO : EstadoToken.ACTIVO;
        
        return TokenRefresh.builder()
                .id(entity.getId())
                .entidad(entity.getEntidad())
                .entidadId(entity.getEntidadId())
                .token(entity.getToken())
                .expiraEn(entity.getExpiraEn())
                .estado(estado)
                .creadoEn(entity.getCreadoEn())
                .build();
    }
    
    public TokenRefreshEntity toEntity(TokenRefresh domain) {
        if (domain == null) return null;
        
        return TokenRefreshEntity.builder()
                .id(domain.getId())
                .entidad(domain.getEntidad())
                .entidadId(domain.getEntidadId())
                .token(domain.getToken())
                .expiraEn(domain.getExpiraEn())
                .revocado(domain.getEstado() == EstadoToken.REVOCADO)
                .creadoEn(domain.getCreadoEn())
                .build();
    }
}