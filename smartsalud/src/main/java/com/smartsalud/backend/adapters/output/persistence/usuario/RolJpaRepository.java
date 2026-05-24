package com.smartsalud.backend.adapters.output.persistence.usuario;

import com.smartsalud.backend.domain.model.usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(Rol nombre);
}
