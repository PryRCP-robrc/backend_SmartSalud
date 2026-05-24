// infrastructure/adapters/output/persistence/mapper/PacienteMapper.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PacienteEntity;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {
    
    public UsuarioPrincipal toDomain(PacienteEntity entity) {
        if (entity == null) return null;
        
        return UsuarioPrincipal.builder()
                .id(entity.getId())
                .dni(entity.getDni())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .password(entity.getPasswordHash())
                .activo(entity.getActivo())
                .build();
    }
    
    public PacienteEntity toEntity(UsuarioPrincipal domain) {
    if (domain == null) return null;
    
    return PacienteEntity.builder()
            .id(domain.getId())
            .dni(domain.getDni())
            .nombres(domain.getNombres())
            .apellidos(domain.getApellidos())
            .email(domain.getEmail())
            .telefono(domain.getTelefono())
            .passwordHash(domain.getPassword())
            .activo(domain.isEnabled())
            // AGREGAR ESTOS CAMPOS CON VALORES POR DEFECTO
            .emailVerificado(false)  // ← IMPORTANTE
            .fechaRegistro(java.time.LocalDateTime.now())
            .build();
    }

}