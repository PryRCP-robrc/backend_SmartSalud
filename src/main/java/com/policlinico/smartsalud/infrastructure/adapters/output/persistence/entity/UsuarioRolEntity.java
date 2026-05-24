// infrastructure/adapters/output/persistence/entity/UsuarioRolEntity.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_rol")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String entidad;
    
    @Column(name = "entidad_id", nullable = false)
    private Long entidadId;
    
    @Column(name = "rol_id", nullable = false)
    private Long rolId;
    
    @Column(name = "asignado_en")
    private LocalDateTime asignadoEn;
}