package com.smartsalud.backend.adapters.output.persistence.cita;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaJpaRepository extends JpaRepository<CitaEntity, Long> {
    List<CitaEntity> findByPacienteId(Long pacienteId);
    List<CitaEntity> findByMedicoId(Long medicoId);
    boolean existsByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end);
}
