// application/service/medico/MedicoService.java
package com.policlinico.smartsalud.application.service.medico;

import com.policlinico.smartsalud.application.ports.input.medico.MedicoInputPort;
import com.policlinico.smartsalud.domain.model.cita.Cita;
import com.policlinico.smartsalud.domain.model.cita.EstadoCita;
import com.policlinico.smartsalud.domain.model.medico.Medico;
import com.policlinico.smartsalud.domain.ports.output.CitaRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.MedicoRepositoryPort;
import com.policlinico.smartsalud.domain.ports.output.PacienteRepositoryPort;
import com.policlinico.smartsalud.domain.model.UsuarioPrincipal;
import com.policlinico.smartsalud.shared.dto.response.medico.CitaMedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.MedicoResponse;
import com.policlinico.smartsalud.shared.dto.response.medico.PacienteMedicoResponse;
import com.policlinico.smartsalud.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoService implements MedicoInputPort {

    private final MedicoRepositoryPort medicoRepository;
    private final CitaRepositoryPort citaRepository;
    private final PacienteRepositoryPort pacienteRepository;

    @Override
    public MedicoResponse getMedicoById(Long medicoId) {
        log.info("Obteniendo médico con ID: {}", medicoId);
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId));
        return toMedicoResponse(medico);
    }

    @Override
    public MedicoResponse getMedicoByEmail(String email) {
        log.info("Obteniendo médico con email: {}", email);
        Medico medico = medicoRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado con email: " + email));
        return toMedicoResponse(medico);
    }

    @Override
    public List<CitaMedicoResponse> getCitasByMedico(Long medicoId) {
        log.info("Obteniendo citas del médico ID: {}", medicoId);
        validarMedicoExiste(medicoId);
        return citaRepository.findByMedicoId(medicoId).stream()
                .map(this::toCitaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaMedicoResponse> getAgendaByFecha(Long medicoId, LocalDate fecha) {
        log.info("Obteniendo agenda del médico ID: {} para fecha: {}", medicoId, fecha);
        validarMedicoExiste(medicoId);
        return citaRepository.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .map(this::toCitaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaMedicoResponse> getAgendaByRango(Long medicoId, LocalDate desde, LocalDate hasta) {
        log.info("Obteniendo agenda del médico ID: {} entre {} y {}", medicoId, desde, hasta);
        validarMedicoExiste(medicoId);
        return citaRepository.findByMedicoIdAndFechaBetween(medicoId, desde, hasta).stream()
                .map(this::toCitaResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PacienteMedicoResponse> getPacientesByMedico(Long medicoId) {
        log.info("Obteniendo pacientes del médico ID: {}", medicoId);
        validarMedicoExiste(medicoId);

        List<Long> pacienteIds = citaRepository.findDistinctPacienteIdsByMedicoId(medicoId);

        // Para cada paciente obtenemos sus datos y estadísticas de citas
        List<Cita> todasLasCitas = citaRepository.findByMedicoId(medicoId);
        Map<Long, List<Cita>> citasPorPaciente = todasLasCitas.stream()
                .filter(c -> c.getEstado() != EstadoCita.CANCELADO && c.getEstado() != EstadoCita.NO_ASISTIO)
                .collect(Collectors.groupingBy(Cita::getPacienteId));

        return pacienteIds.stream()
                .map(pacienteId -> {
                    UsuarioPrincipal paciente = pacienteRepository.findById(pacienteId).orElse(null);
                    if (paciente == null) return null;

                    List<Cita> citasPaciente = citasPorPaciente.getOrDefault(pacienteId, List.of());
                    LocalDate ultimaCita = citasPaciente.stream()
                            .map(Cita::getFecha)
                            .max(Comparator.naturalOrder())
                            .orElse(null);

                    return PacienteMedicoResponse.builder()
                            .id(paciente.getId())
                            .nombres(paciente.getNombres())
                            .apellidos(paciente.getApellidos())
                            .dni(paciente.getDni())
                            .email(paciente.getEmail())
                            .telefono(paciente.getTelefono())
                            .totalCitas((long) citasPaciente.size())
                            .ultimaCita(ultimaCita)
                            .build();
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    private void validarMedicoExiste(Long medicoId) {
        if (medicoRepository.findById(medicoId).isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado con ID: " + medicoId);
        }
    }

    private MedicoResponse toMedicoResponse(Medico m) {
        return MedicoResponse.builder()
                .id(m.getId())
                .nombres(m.getNombres())
                .apellidos(m.getApellidos())
                .cmp(m.getCmp())
                .especialidad(m.getEspecialidadNombre())
                .email(m.getEmail())
                .telefono(m.getTelefono())
                .fotoUrl(m.getFotoUrl())
                .aniosExperiencia(m.getAniosExperiencia())
                .descripcionProfesional(m.getDescripcionProfesional())
                .build();
    }

    private CitaMedicoResponse toCitaResponse(Cita c) {
        return CitaMedicoResponse.builder()
                .id(c.getId())
                .codigoReserva(c.getCodigoReserva())
                .fecha(c.getFecha())
                .hora(c.getHora())
                .duracionMin(c.getDuracionMin())
                .tipoConsulta(c.getTipoConsulta())
                .modalidad(c.getModalidad())
                .estado(c.getEstado() != null ? c.getEstado().name() : null)
                .motivoConsulta(c.getMotivoConsulta())
                .sede(c.getSedeNombre())
                .sala(c.getSalaNombre())
                .pacienteId(c.getPacienteId())
                .pacienteNombres(c.getPacienteNombres())
                .pacienteApellidos(c.getPacienteApellidos())
                .pacienteDni(c.getPacienteDni())
                .pacienteTelefono(c.getPacienteTelefono())
                .pacienteEmail(c.getPacienteEmail())
                .build();
    }
}
