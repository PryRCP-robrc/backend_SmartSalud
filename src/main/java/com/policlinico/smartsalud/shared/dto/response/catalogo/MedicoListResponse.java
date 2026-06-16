package com.policlinico.smartsalud.shared.dto.response.catalogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicoListResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String cmp;
    private Long especialidadId;
    private String especialidadNombre;
    private String email;
    private String telefono;
    private String fotoUrl;
    private Integer aniosExperiencia;
    private String descripcionProfesional;
}
