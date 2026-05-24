// domain/ports/output/UsuarioRolRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.Rol;
import java.util.List;

public interface UsuarioRolRepositoryPort {
    void assignRolToUsuario(String entidad, Long entidadId, Long rolId);
    List<Rol> findRolesByEntidad(String entidad, Long entidadId);
}