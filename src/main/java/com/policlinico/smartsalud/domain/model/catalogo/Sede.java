package com.policlinico.smartsalud.domain.model.catalogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sede {
    private Long id;
    private String nombre;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String telefono;
    private String email;
    private Boolean activa;
}
