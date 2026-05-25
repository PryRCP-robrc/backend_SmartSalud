// domain/ports/output/CitaRepositoryPort.java
package com.policlinico.smartsalud.domain.ports.output;

import com.policlinico.smartsalud.domain.model.cita.Cita;
import java.time.LocalDate;
import java.util.List;

public interface CitaRepositoryPort {
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha);
    List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDate desde, LocalDate hasta);
    List<Long> findDistinctPacienteIdsByMedicoId(Long medicoId);
}
