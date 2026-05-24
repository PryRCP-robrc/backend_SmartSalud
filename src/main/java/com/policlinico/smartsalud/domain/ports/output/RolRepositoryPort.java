// domain/ports/output/RolRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.Rol;
import com.policlinico.smartsalud.domain.model.enums.NombreRol;
import java.util.Optional;

public interface RolRepositoryPort {
    Optional<Rol> findByNombre(NombreRol nombre);
    Rol save(Rol rol);
}