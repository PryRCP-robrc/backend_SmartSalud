package com.policlinico.smartsalud.shared.dto.response.cita;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class CitaResponse {
    private Long id;
    private String codigoReserva;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;
    private String tipoConsulta;
    private String modalidad;
    private String estado;
    private String motivoConsulta;

    // Datos del médico
    private Long medicoId;
    private String medicoNombres;
    private String medicoApellidos;
    private String especialidad;

    // Sede
    private Long sedeId;
    private String sedeNombre;

    // Paciente
    private Long pacienteId;
    private String pacienteNombres;
    private String pacienteApellidos;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCancelacion;
    private String motivoCancelacion;
}
