package com.policlinico.smartsalud.application.service.cita;

import com.policlinico.smartsalud.application.ports.input.cita.CitaInputPort;
import com.policlinico.smartsalud.domain.model.catalogo.Sede;
import com.policlinico.smartsalud.domain.model.cita.Cita;
import com.policlinico.smartsalud.domain.model.cita.EstadoCita;
import com.policlinico.smartsalud.domain.ports.output.CitaRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.SedeRepositoryPort;
import com.policlinico.smartsalud.shared.dto.request.cita.CancelarCitaRequest;
import com.policlinico.smartsalud.shared.dto.request.cita.CrearCitaRequest;
import com.policlinico.smartsalud.shared.dto.response.cita.CitaResponse;
import com.policlinico.smartsalud.shared.dto.response.horario.SlotDisponibleResponse;
import com.policlinico.smartsalud.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CitaService implements CitaInputPort {

    private final CitaRepositoryPort citaRepo;
    private final SedeRepositoryPort sedeRepo;
    private final MedicoRepositoryPort medicoRepo;

    @Override
    @Transactional
    public CitaResponse crear(CrearCitaRequest request) {
        log.info("Crear cita paciente={} medico={} fecha={} hora={}",
                request.getPacienteId(), request.getMedicoId(), request.getFecha(), request.getHora());

        if (citaRepo.existsByMedicoIdAndFechaAndHora(request.getMedicoId(), request.getFecha(), request.getHora())) {
            throw new IllegalStateException("Ya existe una cita reservada en ese horario para este médico");
        }

        Cita nueva = Cita.builder()
                .pacienteId(request.getPacienteId())
                .medicoId(request.getMedicoId())
                .sedeId(request.getSedeId())
                .fecha(request.getFecha())
                .hora(request.getHora())
                .duracionMin(request.getDuracionMin())
                .tipoConsulta(request.getTipoConsulta())
                .modalidad(request.getModalidad())
                .estado(EstadoCita.RESERVADO)
                .motivoConsulta(request.getMotivoConsulta())
                .origen("WEB")
                .build();

        Cita saved = citaRepo.save(nueva);
        return toResponse(saved);
    }

    @Override
    public CitaResponse obtenerPorId(Long id) {
        return citaRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada: " + id));
    }

    @Override
    public List<CitaResponse> listarPorPaciente(Long pacienteId) {
        return citaRepo.findByPacienteId(pacienteId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public CitaResponse cancelar(Long id, CancelarCitaRequest request) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada: " + id));
        if (cita.getEstado() == EstadoCita.CANCELADO || cita.getEstado() == EstadoCita.ATENDIDO) {
            throw new IllegalStateException("La cita ya no se puede cancelar (estado actual: " + cita.getEstado() + ")");
        }
        cita.setEstado(EstadoCita.CANCELADO);
        cita.setFechaCancelacion(LocalDateTime.now());
        cita.setMotivoCancelacion(request.getMotivo());
        cita.setCanceladoPor(request.getCanceladoPor());
        Cita saved = citaRepo.save(cita);
        return toResponse(saved);
    }

    @Override
    public List<SlotDisponibleResponse> slotsDisponibles(Long medicoId, LocalDate fecha) {
        // Generamos slots de 30 min entre 09:00 y 18:00 (rango típico de policlínico)
        var ocupadas = citaRepo.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .filter(c -> c.getEstado() != EstadoCita.CANCELADO && c.getEstado() != EstadoCita.NO_ASISTIO)
                .map(Cita::getHora)
                .toList();

        Sede primeraSede = sedeRepo.findAllActivas().stream().findFirst().orElse(null);
        Long sedeId = primeraSede != null ? primeraSede.getId() : 1L;
        String sedeNombre = primeraSede != null ? primeraSede.getNombre() : "Sede Central";

        List<SlotDisponibleResponse> slots = new ArrayList<>();
        for (int h = 9; h < 18; h++) {
            for (int m = 0; m < 60; m += 30) {
                LocalTime hora = LocalTime.of(h, m);
                boolean disponible = !ocupadas.contains(hora);
                slots.add(SlotDisponibleResponse.builder()
                        .fecha(fecha)
                        .hora(hora)
                        .duracionMin(30)
                        .medicoId(medicoId)
                        .sedeId(sedeId)
                        .sedeNombre(sedeNombre)
                        .disponible(disponible)
                        .build());
            }
        }
        return slots;
    }

    private CitaResponse toResponse(Cita c) {
        return CitaResponse.builder()
                .id(c.getId())
                .codigoReserva(c.getCodigoReserva())
                .fecha(c.getFecha())
                .hora(c.getHora())
                .duracionMin(c.getDuracionMin())
                .tipoConsulta(c.getTipoConsulta())
                .modalidad(c.getModalidad())
                .estado(c.getEstado() != null ? c.getEstado().name() : null)
                .motivoConsulta(c.getMotivoConsulta())
                .medicoId(c.getMedicoId())
                .medicoNombres(c.getMedicoNombres())
                .medicoApellidos(c.getMedicoApellidos())
                .especialidad(c.getMedicoEspecialidad())
                .sedeId(c.getSedeId())
                .sedeNombre(c.getSedeNombre())
                .pacienteId(c.getPacienteId())
                .pacienteNombres(c.getPacienteNombres())
                .pacienteApellidos(c.getPacienteApellidos())
                .fechaCreacion(c.getFechaCreacion())
                .fechaCancelacion(c.getFechaCancelacion())
                .motivoCancelacion(c.getMotivoCancelacion())
                .build();
    }
}
