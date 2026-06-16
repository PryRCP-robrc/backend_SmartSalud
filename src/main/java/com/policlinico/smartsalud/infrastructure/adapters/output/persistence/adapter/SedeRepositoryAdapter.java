package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.catalogo.Sede;
import com.policlinico.smartsalud.domain.ports.output.SedeRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.SedeEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaSedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SedeRepositoryAdapter implements SedeRepositoryPort {

    private final JpaSedeRepository repo;

    @Override
    public List<Sede> findAllActivas() {
        return repo.findByActivaTrueOrderByNombreAsc().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Sede> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    private Sede toDomain(SedeEntity e) {
        return Sede.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .direccion(e.getDireccion())
                .distrito(e.getDistrito())
                .ciudad(e.getCiudad())
                .telefono(e.getTelefono())
                .email(e.getEmail())
                .activa(e.getActiva())
                .build();
    }
}
