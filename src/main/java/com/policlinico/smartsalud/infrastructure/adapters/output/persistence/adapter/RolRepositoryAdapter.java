// infrastructure/adapters/output/persistence/adapter/RolRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import com.policlinico.smartsalud.domain.ports.output.RolRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.RolMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolRepositoryAdapter implements RolRepositoryPort {
    
    private final JpaRolRepository jpaRepository;
    private final RolMapper mapper;
    
    @Override
    public Optional<Rol> findByNombre(NombreRol nombre) {
        return jpaRepository.findByNombre(nombre.name()).map(mapper::toDomain);
    }
    
    @Override
    public Rol save(Rol rol) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(rol)));
    }
}