package com.policlinico.smartsalud.shared.dto.request.cita;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelarCitaRequest {
    @NotBlank
    private String motivo;
    private String canceladoPor = "PACIENTE";
}
