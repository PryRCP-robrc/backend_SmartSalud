// infrastructure/adapters/output/persistence/repository/JpaMedicoRepository.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JpaMedicoRepository extends JpaRepository<MedicoEntity, Long> {
    Optional<MedicoEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT m FROM MedicoEntity m JOIN FETCH m.especialidad WHERE m.activo = true ORDER BY m.apellidos ASC")
    List<MedicoEntity> findAllActivos();

    @Query("SELECT m FROM MedicoEntity m JOIN FETCH m.especialidad WHERE m.activo = true AND m.especialidad.id = :especialidadId ORDER BY m.apellidos ASC")
    List<MedicoEntity> findAllActivosPorEspecialidad(@Param("especialidadId") Long especialidadId);
}
