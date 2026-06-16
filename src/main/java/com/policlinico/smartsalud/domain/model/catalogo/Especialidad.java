package com.policlinico.smartsalud.domain.model.catalogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Especialidad {
    private Long id;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
    private Boolean activa;
}
