package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface JpaPagoRepository extends JpaRepository<PagoEntity, Long> {
    Optional<PagoEntity> findByCitaId(Long citaId);

    @Query("SELECT p FROM PagoEntity p WHERE p.citaId IN (SELECT c.id FROM CitaEntity c WHERE c.paciente.id = :pacienteId) ORDER BY p.fecha DESC")
    List<PagoEntity> findAllByPacienteId(@Param("pacienteId") Long pacienteId);
}
