package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.adapter;

import com.policlinico.smartsalud.domain.model.cita.Cita;
import com.policlinico.smartsalud.domain.model.cita.EstadoCita;
import com.policlinico.smartsalud.domain.ports.output.CitaRepositoryPort;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.CitaEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.MedicoEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PacienteEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper.CitaMapper;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaCitaRepository;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaMedicoRepository;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaPacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CitaRepositoryAdapter implements CitaRepositoryPort {

    private final JpaCitaRepository jpaRepository;
    private final JpaPacienteRepository pacienteRepository;
    private final JpaMedicoRepository medicoRepository;
    private final CitaMapper mapper;

    @Override
    public List<Cita> findByMedicoId(Long medicoId) {
        return jpaRepository.findByMedicoIdOrderByFechaDescHoraAsc(medicoId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha) {
        return jpaRepository.findByMedicoIdAndFecha(medicoId, fecha).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Cita> findByMedicoIdAndFechaBetween(Long medicoId, LocalDate desde, LocalDate hasta) {
        return jpaRepository.findByMedicoIdAndFechaBetween(medicoId, desde, hasta).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Long> findDistinctPacienteIdsByMedicoId(Long medicoId) {
        return jpaRepository.findDistinctPacienteIdsByMedicoId(medicoId);
    }

    @Override
    public List<Cita> findByPacienteId(Long pacienteId) {
        return jpaRepository.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Cita> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora) {
        return jpaRepository.existsByMedicoIdAndFechaAndHora(medicoId, fecha, hora);
    }

    @Override
    public Cita save(Cita cita) {
        CitaEntity entity;
        if (cita.getId() != null) {
            entity = jpaRepository.findById(cita.getId()).orElseGet(CitaEntity::new);
        } else {
            entity = new CitaEntity();
            entity.setCodigoReserva(generarCodigoReserva());
            entity.setFechaCreacion(LocalDateTime.now());
            entity.setOrigen(cita.getOrigen() != null ? cita.getOrigen() : "WEB");
        }

        // Resolver relaciones
        PacienteEntity paciente = pacienteRepository.findById(cita.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado: " + cita.getPacienteId()));
        MedicoEntity medico = medicoRepository.findById(cita.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado: " + cita.getMedicoId()));

        entity.setPaciente(paciente);
        entity.setMedico(medico);
        entity.setSedeId(cita.getSedeId());
        entity.setSalaId(cita.getSalaId());
        entity.setFecha(cita.getFecha());
        entity.setHora(cita.getHora());
        entity.setDuracionMin(cita.getDuracionMin() != null ? cita.getDuracionMin() : 30);
        entity.setTipoConsulta(cita.getTipoConsulta() != null ? cita.getTipoConsulta() : "PRIMERA_VEZ");
        entity.setModalidad(cita.getModalidad() != null ? cita.getModalidad() : "PRESENCIAL");
        entity.setEstado(cita.getEstado() != null ? cita.getEstado().name() : "RESERVADO");
        entity.setMotivoConsulta(cita.getMotivoConsulta());
        entity.setNotasAdmin(cita.getNotasAdmin());
        entity.setFechaCancelacion(cita.getFechaCancelacion());
        entity.setMotivoCancelacion(cita.getMotivoCancelacion());
        entity.setCanceladoPor(cita.getCanceladoPor());

        CitaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    private String generarCodigoReserva() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
