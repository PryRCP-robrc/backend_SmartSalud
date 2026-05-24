// domain/ports/output/PacienteRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import java.util.Optional;

public interface PacienteRepositoryPort {
    Optional<UsuarioPrincipal> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    UsuarioPrincipal save(UsuarioPrincipal usuario);
    Optional<UsuarioPrincipal> findById(Long id);
}