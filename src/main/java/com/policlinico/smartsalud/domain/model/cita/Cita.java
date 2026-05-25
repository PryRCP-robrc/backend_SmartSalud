// domain/model/cita/Cita.java
package com.policlinico.smartsalud.domain.model.cita;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class Cita {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private Long sedeId;
    private Long salaId;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;
    private String tipoConsulta;
    private String modalidad;
    private EstadoCita estado;
    private String motivoConsulta;
    private String notasAdmin;
    private String codigoReserva;
    private String origen;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCancelacion;
    private String motivoCancelacion;
    private String canceladoPor;

    // Datos relacionales denormalizados para respuestas
    private String pacienteNombres;
    private String pacienteApellidos;
    private String pacienteDni;
    private String pacienteTelefono;
    private String pacienteEmail;
    private String sedeNombre;
    private String salaNombre;
}
