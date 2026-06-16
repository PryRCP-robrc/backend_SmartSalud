// domain/ports/output/MedicoRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.medico.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoRepositoryPort {
    Optional<Medico> findById(Long id);
    Optional<Medico> findByEmail(String email);
    List<Medico> findAll();
    List<Medico> findAllActivos(Long especialidadId);
    boolean existsByEmail(String email);
}
