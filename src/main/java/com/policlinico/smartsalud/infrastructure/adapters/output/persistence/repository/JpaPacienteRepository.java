// infrastructure/adapters/output/persistence/repository/JpaPacienteRepository.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPacienteRepository extends JpaRepository<PacienteEntity, Long> {
    Optional<PacienteEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}