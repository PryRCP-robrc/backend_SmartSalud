package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.cita.Cita;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepositoryPort {
    // Médico
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha);
    List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDate desde, LocalDate hasta);
    List<Long> findDistinctPacienteIdsByMedicoId(Long medicoId);

    // Paciente
    List<Cita> findByPacienteId(Long pacienteId);

    // CRUD
    Optional<Cita> findById(Long id);
    Cita save(Cita cita);
    boolean existsByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora);
}
