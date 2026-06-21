// domain/model/medico/Medico.java
package com.policlinico.smartsalud.domain.model.medico;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Medico {
    private Long id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String cmp;
    private Long especialidadId;
    private String especialidadNombre;
    private String telefono;
    private String email;
    private String fotoUrl;
    private Integer aniosExperiencia;
    private String descripcionProfesional;
    private Boolean activo;
    private LocalDate fechaIngreso;
    private String passwordHash;
    private BigDecimal tarifaConsulta;
}
