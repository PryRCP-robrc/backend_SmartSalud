// shared/dto/response/medico/CitaMedicoResponse.java
package com.policlinico.smartsalud.shared.dto.response.medico;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CitaMedicoResponse {
    private Long id;
    private String codigoReserva;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;
    private String tipoConsulta;
    private String modalidad;
    private String estado;
    private String motivoConsulta;
    private String sede;
    private String sala;

    private Long pacienteId;
    private String pacienteNombres;
    private String pacienteApellidos;
    private String pacienteDni;
    private String pacienteTelefono;
    private String pacienteEmail;
}
