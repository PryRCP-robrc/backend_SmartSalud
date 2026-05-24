package com.smartsalud.backend.domain.port.in.cita;

import com.smartsalud.backend.domain.model.cita.Cita;
import java.util.List;

public interface ReservaCitaUseCase {
    Cita reservarCita(Cita cita);
    Cita cancelarCita(Long citaId);
    List<Cita> obtenerCitasPorPaciente(Long pacienteId);
    List<Cita> obtenerCitasPorMedico(Long medicoId);
}
