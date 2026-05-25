// infrastructure/adapters/output/persistence/entity/MedicoEntity.java
package com.policlinico.smartsalud.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "medico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 20)
    private String cmp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private EspecialidadEntity especialidad;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(name = "descripcion_profesional")
    private String descripcionProfesional;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    // Columna agregada para login médico (ver script SQL add-medico-auth.sql)
    @Column(name = "password_hash")
    private String passwordHash;
}
