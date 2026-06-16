package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.catalogo.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadRepositoryPort {
    List<Especialidad> findAllActivas();
    Optional<Especialidad> findById(Long id);
}
