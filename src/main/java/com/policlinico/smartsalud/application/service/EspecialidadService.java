package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.entity.Especialidad;
import com.policlinico.smartsalud.domain.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.policlinico.smartsalud.application.dto.EspecialidadDTO;

@Service
@RequiredArgsConstructor
public class EspecialidadService {
    private final EspecialidadRepository repository;

    public List<Especialidad> getEspecialidadesActivas() {
        return repository.findByActivaTrue();
    }

    public EspecialidadDTO createEspecialidad(com.policlinico.smartsalud.application.dto.EspecialidadRequest request) {
        Especialidad e = new Especialidad();
        e.setNombre(request.getNombre());
        e.setDescripcion(request.getDescripcion());
        e.setIconoUrl(request.getIconoUrl());
        e.setActiva(true);
        e = repository.save(e);
        return new EspecialidadDTO(e.getId(), e.getNombre(), e.getDescripcion(), e.getIconoUrl());
    }

    public EspecialidadDTO updateEspecialidad(Integer id, com.policlinico.smartsalud.application.dto.EspecialidadRequest request) {
        Especialidad e = repository.findById(id).orElseThrow();
        e.setNombre(request.getNombre());
        e.setDescripcion(request.getDescripcion());
        e.setIconoUrl(request.getIconoUrl());
        e = repository.save(e);
        return new EspecialidadDTO(e.getId(), e.getNombre(), e.getDescripcion(), e.getIconoUrl());
    }

    public void deleteEspecialidad(Integer id) {
        Especialidad e = repository.findById(id).orElseThrow();
        e.setActiva(false);
        repository.save(e);
    }
}
