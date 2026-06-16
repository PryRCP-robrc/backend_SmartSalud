package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.mapper;

import com.policlinico.smartsalud.domain.model.cita.Cita;
import com.policlinico.smartsalud.domain.model.cita.EstadoCita;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.CitaEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.MedicoEntity;
import com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity.PacienteEntity;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toDomain(CitaEntity entity) {
        if (entity == null) return null;

        PacienteEntity p = entity.getPaciente();
        MedicoEntity m = entity.getMedico();

        return Cita.builder()
                .id(entity.getId())
                .pacienteId(p != null ? p.getId() : null)
                .medicoId(m != null ? m.getId() : null)
                .sedeId(entity.getSedeId())
                .salaId(entity.getSalaId())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .duracionMin(entity.getDuracionMin())
                .tipoConsulta(entity.getTipoConsulta())
                .modalidad(entity.getModalidad())
                .estado(entity.getEstado() != null ? EstadoCita.valueOf(entity.getEstado()) : null)
                .motivoConsulta(entity.getMotivoConsulta())
                .notasAdmin(entity.getNotasAdmin())
                .codigoReserva(entity.getCodigoReserva())
                .origen(entity.getOrigen())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaCancelacion(entity.getFechaCancelacion())
                .motivoCancelacion(entity.getMotivoCancelacion())
                .canceladoPor(entity.getCanceladoPor())
                .pacienteNombres(p != null ? p.getNombres() : null)
                .pacienteApellidos(p != null ? p.getApellidos() : null)
                .pacienteDni(p != null ? p.getDni() : null)
                .pacienteTelefono(p != null ? p.getTelefono() : null)
                .pacienteEmail(p != null ? p.getEmail() : null)
                .medicoNombres(m != null ? m.getNombres() : null)
                .medicoApellidos(m != null ? m.getApellidos() : null)
                .medicoEspecialidad(m != null && m.getEspecialidad() != null ? m.getEspecialidad().getNombre() : null)
                .build();
    }
}
