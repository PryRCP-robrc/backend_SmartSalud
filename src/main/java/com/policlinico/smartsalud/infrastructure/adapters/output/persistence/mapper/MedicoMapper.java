// infrastructure/adapters/output/persistence/mapper/MedicoMapper.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.domain.model.medico.Medico;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.MedicoEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public Medico toDomain(MedicoEntity entity) {
        if (entity == null) return null;

        return Medico.builder()
                .id(entity.getId())
                .dni(entity.getDni())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .cmp(entity.getCmp())
                .especialidadId(entity.getEspecialidad() != null ? entity.getEspecialidad().getId() : null)
                .especialidadNombre(entity.getEspecialidad() != null ? entity.getEspecialidad().getNombre() : null)
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .fotoUrl(entity.getFotoUrl())
                .aniosExperiencia(entity.getAniosExperiencia())
                .descripcionProfesional(entity.getDescripcionProfesional())
                .activo(entity.getActivo())
                .fechaIngreso(entity.getFechaIngreso())
                .build();
    }
}
