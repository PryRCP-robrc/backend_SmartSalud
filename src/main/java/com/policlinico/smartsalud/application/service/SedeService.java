package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.entity.Sede;
import com.policlinico.smartsalud.domain.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.policlinico.smartsalud.application.dto.SedeDTO;

@Service
@RequiredArgsConstructor
public class SedeService {
    private final SedeRepository repository;

    public List<Sede> getSedesActivas() {
        return repository.findByActivaTrue();
    }

    public SedeDTO createSede(com.policlinico.smartsalud.application.dto.SedeRequest request) {
        Sede s = new Sede();
        s.setNombre(request.getNombre());
        s.setDireccion(request.getDireccion());
        s.setDistrito(request.getDistrito());
        s.setCiudad(request.getCiudad());
        s.setTelefono(request.getTelefono());
        s.setEmail(request.getEmail());
        s.setActiva(true);
        s = repository.save(s);
        return new SedeDTO(s.getId(), s.getNombre(), s.getDireccion(), s.getDistrito(), s.getCiudad());
    }

    public SedeDTO updateSede(Integer id, com.policlinico.smartsalud.application.dto.SedeRequest request) {
        Sede s = repository.findById(id).orElseThrow();
        s.setNombre(request.getNombre());
        s.setDireccion(request.getDireccion());
        s.setDistrito(request.getDistrito());
        s.setCiudad(request.getCiudad());
        s.setTelefono(request.getTelefono());
        s.setEmail(request.getEmail());
        s = repository.save(s);
        return new SedeDTO(s.getId(), s.getNombre(), s.getDireccion(), s.getDistrito(), s.getCiudad());
    }

    public void deleteSede(Integer id) {
        Sede s = repository.findById(id).orElseThrow();
        s.setActiva(false);
        repository.save(s);
    }
}
