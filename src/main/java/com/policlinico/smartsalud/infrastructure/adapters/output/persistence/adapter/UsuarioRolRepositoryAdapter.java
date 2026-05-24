// infrastructure/adapters/output/persistence/adapter/UsuarioRolRepositoryAdapter.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.ports.output.UsuarioRolRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.RolEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.UsuarioRolEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.RolMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaRolRepository;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaUsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;  // ← ESTE IMPORT FALTABA
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioRolRepositoryAdapter implements UsuarioRolRepositoryPort {
    
    private final JpaUsuarioRolRepository jpaRepository;
    private final JpaRolRepository jpaRolRepository;
    private final RolMapper rolMapper;
    
    @Override
    @Transactional
    public void assignRolToUsuario(String entidad, Long entidadId, Long rolId) {
        UsuarioRolEntity entity = UsuarioRolEntity.builder()
                .entidad(entidad)
                .entidadId(entidadId)
                .rolId(rolId)
                .asignadoEn(LocalDateTime.now())
                .build();
        jpaRepository.save(entity);
    }
    
    @Override
    public List<Rol> findRolesByEntidad(String entidad, Long entidadId) {
        List<UsuarioRolEntity> usuarioRoles = jpaRepository.findByEntidadAndEntidadId(entidad, entidadId);
        
        return usuarioRoles.stream()
                .map(ur -> jpaRolRepository.findById(ur.getRolId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(rolMapper::toDomain)
                .collect(Collectors.toList());
    }
}