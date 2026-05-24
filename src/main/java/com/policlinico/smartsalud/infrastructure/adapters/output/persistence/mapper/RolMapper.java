// infrastructure/adapters/output/persistence/mapper/RolMapper.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.RolEntity;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {
    
    public Rol toDomain(RolEntity entity) {
        if (entity == null) return null;
        
        return Rol.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();
    }
    
    public RolEntity toEntity(Rol domain) {
        if (domain == null) return null;
        
        return RolEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .descripcion(domain.getDescripcion())
                .build();
    }
}