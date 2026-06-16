package com.policlinico.smartsalud.shared.dto.response.catalogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SedeResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String telefono;
}
