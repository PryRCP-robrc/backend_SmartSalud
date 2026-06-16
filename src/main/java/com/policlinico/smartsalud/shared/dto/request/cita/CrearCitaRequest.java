package com.policlinico.smartsalud.shared.dto.request.cita;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CrearCitaRequest {
    @NotNull
    private Long pacienteId;

    @NotNull
    private Long medicoId;

    @NotNull
    private Long sedeId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime hora;

    private Integer duracionMin = 30;
    private String tipoConsulta = "PRIMERA_VEZ";
    private String modalidad = "PRESENCIAL";
    private String motivoConsulta;
}
