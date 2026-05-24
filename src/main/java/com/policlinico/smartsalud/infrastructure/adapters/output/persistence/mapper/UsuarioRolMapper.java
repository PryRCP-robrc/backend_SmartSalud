// infrastructure/adapters/output/persistence/mapper/UsuarioRolMapper.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.UsuarioRolEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRolMapper {
    
    /**
     * Converts UsuarioRolEntity to a simplified domain object (if needed)
     * Currently, UsuarioRol is only used at infrastructure level,
     * so no domain model is required.
     */
    
    public UsuarioRolEntity toEntity(String entidad, Long entidadId, Long rolId) {
        if (entidad == null || entidadId == null || rolId == null) {
            return null;
        }
        
        return UsuarioRolEntity.builder()
                .entidad(entidad)
                .entidadId(entidadId)
                .rolId(rolId)
                .asignadoEn(java.time.LocalDateTime.now())
                .build();
    }
    
    /**
     * Extract rolId from entity
     */
    public Long getRolIdFromEntity(UsuarioRolEntity entity) {
        return entity != null ? entity.getRolId() : null;
    }
    
    /**
     * Extract entidadId from entity
     */
    public Long getEntidadIdFromEntity(UsuarioRolEntity entity) {
        return entity != null ? entity.getEntidadId() : null;
    }
    
    /**
     * Extract entidad from entity
     */
    public String getEntidadFromEntity(UsuarioRolEntity entity) {
        return entity != null ? entity.getEntidad() : null;
    }
}