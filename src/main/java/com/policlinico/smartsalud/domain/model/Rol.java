// domain/model/Rol.java
package com.policlinico.smartsalud.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rol {
    private Long id;
    private String nombre;
    private String descripcion;
}