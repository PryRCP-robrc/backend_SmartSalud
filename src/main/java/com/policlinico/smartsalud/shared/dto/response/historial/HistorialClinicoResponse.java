package com.policlinico.smartsalud.shared.dto.response.historial;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class HistorialClinicoResponse {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private Long citaId;
    private LocalDate fecha;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private LocalDate proximaCita;
    private LocalDateTime creadoEn;
}
