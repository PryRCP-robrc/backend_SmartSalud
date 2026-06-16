package com.policlinico.smartsalud.application.service.historial;

import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.HistorialClinicoEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.repository.JpaHistorialClinicoRepository;
import com.policlinico.smartsalud.shared.dto.request.historial.CrearHistorialRequest;
import com.policlinico.smartsalud.shared.dto.response.historial.HistorialClinicoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialClinicoService {

    private final JpaHistorialClinicoRepository repo;

    @Transactional
    public HistorialClinicoResponse crear(CrearHistorialRequest request) {
        log.info("Crear historial paciente={} medico={}", request.getPacienteId(), request.getMedicoId());

        HistorialClinicoEntity entity = HistorialClinicoEntity.builder()
                .pacienteId(request.getPacienteId())
                .medicoId(request.getMedicoId())
                .citaId(request.getCitaId())
                .fecha(request.getFecha() != null ? request.getFecha() : LocalDate.now())
                .motivoConsulta(request.getMotivoConsulta())
                .diagnostico(request.getDiagnostico())
                .tratamiento(request.getTratamiento())
                .observaciones(request.getObservaciones())
                .proximaCita(request.getProximaCita())
                .creadoEn(LocalDateTime.now())
                .build();

        return toResponse(repo.save(entity));
    }

    public List<HistorialClinicoResponse> listarPorPaciente(Long pacienteId) {
        return repo.findByPacienteIdOrderByFechaDesc(pacienteId).stream().map(this::toResponse).toList();
    }

    private HistorialClinicoResponse toResponse(HistorialClinicoEntity e) {
        return HistorialClinicoResponse.builder()
                .id(e.getId())
                .pacienteId(e.getPacienteId())
                .medicoId(e.getMedicoId())
                .citaId(e.getCitaId())
                .fecha(e.getFecha())
                .motivoConsulta(e.getMotivoConsulta())
                .diagnostico(e.getDiagnostico())
                .tratamiento(e.getTratamiento())
                .observaciones(e.getObservaciones())
                .proximaCita(e.getProximaCita())
                .creadoEn(e.getCreadoEn())
                .build();
    }
}
