// infrastructure/adapters/output/persistence/adapter/PacienteRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.domain.ports.output.PacienteRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PacienteEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.PacienteMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaPacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PacienteRepositoryAdapter implements PacienteRepositoryPort {
    
    private final JpaPacienteRepository jpaRepository;
    private final PacienteMapper mapper;
    
    @Override
    public Optional<UsuarioPrincipal> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByDni(String dni) {
        return jpaRepository.existsByDni(dni);
    }
    
    @Override
    public UsuarioPrincipal save(UsuarioPrincipal usuario) {
        PacienteEntity entity = mapper.toEntity(usuario);
        PacienteEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<UsuarioPrincipal> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}