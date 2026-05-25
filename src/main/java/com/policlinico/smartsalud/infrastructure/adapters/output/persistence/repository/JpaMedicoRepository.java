// infrastructure/adapters/output/persistence/repository/JpaMedicoRepository.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaMedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Optional<MedicoEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
