package com.policlinico.smartsalud.shared.dto.response.catalogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EspecialidadResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
}
