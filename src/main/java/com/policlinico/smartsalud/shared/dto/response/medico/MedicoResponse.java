// shared/dto/response/medico/MedicoResponse.java
package com.policlinico.smartsalud.shared.dto.response.medico;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicoResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String cmp;
    private String especialidad;
    private String email;
    private String telefono;
    private String fotoUrl;
    private Integer aniosExperiencia;
    private String descripcionProfesional;
}
