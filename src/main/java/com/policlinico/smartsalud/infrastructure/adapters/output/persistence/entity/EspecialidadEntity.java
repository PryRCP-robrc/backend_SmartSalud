// infrastructure/adapters/output/persistence/entity/EspecialidadEntity.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especialidad")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String descripcion;

    @Column(name = "icono_url", length = 255)
    private String iconoUrl;

    @Column(nullable = false)
    private Boolean activa;
}
