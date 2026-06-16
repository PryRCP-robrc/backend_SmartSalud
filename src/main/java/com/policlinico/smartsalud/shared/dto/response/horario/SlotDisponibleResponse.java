package com.policlinico.smartsalud.shared.dto.response.horario;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class SlotDisponibleResponse {
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;
    private Long medicoId;
    private Long sedeId;
    private String sedeNombre;
    private Boolean disponible;
}
