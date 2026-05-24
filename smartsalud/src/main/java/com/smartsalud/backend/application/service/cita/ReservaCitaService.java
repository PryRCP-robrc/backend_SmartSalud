package com.smartsalud.backend.application.service.cita;

import com.smartsalud.backend.domain.model.cita.Cita;
import com.smartsalud.backend.domain.model.cita.EstadoCita;
import com.smartsalud.backend.domain.port.in.cita.ReservaCitaUseCase;
import com.smartsalud.backend.domain.port.out.cita.CitaRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaCitaService implements ReservaCitaUseCase {

    private final CitaRepositoryPort citaRepositoryPort;

    public ReservaCitaService(CitaRepositoryPort citaRepositoryPort) {
        this.citaRepositoryPort = citaRepositoryPort;
    }

    @Override
    public Cita reservarCita(Cita cita) {
        // Simple validación de cruce de horarios: asumimos citas de 30 mins
        LocalDateTime start = cita.getFechaHora().minusMinutes(29);
        LocalDateTime end = cita.getFechaHora().plusMinutes(29);

        if (citaRepositoryPort.existsByMedicoIdAndFechaHoraBetween(cita.getMedicoId(), start, end)) {
            throw new RuntimeException("El médico ya tiene una cita en ese horario");
        }

        cita.setEstado(EstadoCita.PROGRAMADA);
        return citaRepositoryPort.save(cita);
    }

    @Override
    public Cita cancelarCita(Long citaId) {
        Cita cita = citaRepositoryPort.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado(EstadoCita.CANCELADA);
        return citaRepositoryPort.save(cita);
    }

    @Override
    public List<Cita> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepositoryPort.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> obtenerCitasPorMedico(Long medicoId) {
        return citaRepositoryPort.findByMedicoId(medicoId);
    }
}
