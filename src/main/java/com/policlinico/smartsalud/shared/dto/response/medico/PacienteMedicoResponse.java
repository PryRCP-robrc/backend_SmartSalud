// shared/dto/response/medico/PacienteMedicoResponse.java
package com.policlinico.smartsalud.shared.dto.response.medico;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class PacienteMedicoResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String sexo;
    private Long totalCitas;
    private LocalDate ultimaCita;
}
