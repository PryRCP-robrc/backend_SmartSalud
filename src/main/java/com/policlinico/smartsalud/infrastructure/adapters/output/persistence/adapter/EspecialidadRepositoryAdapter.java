package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.catalogo.Especialidad;
import com.policlinico.smartsalud.domain.ports.output.EspecialidadRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.EspecialidadEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaEspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EspecialidadRepositoryAdapter implements EspecialidadRepositoryPort {

    private final JpaEspecialidadRepository repo;

    @Override
    public List<Especialidad> findAllActivas() {
        return repo.findByActivaTrueOrderByNombreAsc().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Especialidad> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    private Especialidad toDomain(EspecialidadEntity e) {
        return Especialidad.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .iconoUrl(e.getIconoUrl())
                .activa(e.getActiva())
                .build();
    }
}
