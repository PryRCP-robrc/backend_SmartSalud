package com.smartsalud.backend.domain.port.out.cita;

import com.smartsalud.backend.domain.model.cita.Cita;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepositoryPort {
    Cita save(Cita cita);
    Optional<Cita> findById(Long id);
    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByMedicoId(Long medicoId);
    boolean existsByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end);
}
