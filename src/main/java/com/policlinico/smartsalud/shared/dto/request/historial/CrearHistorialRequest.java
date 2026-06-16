package com.policlinico.smartsalud.shared.dto.request.historial;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CrearHistorialRequest {
    @NotNull
    private Long pacienteId;
    @NotNull
    private Long medicoId;
    private Long citaId;
    private LocalDate fecha;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private LocalDate proximaCita;
}
